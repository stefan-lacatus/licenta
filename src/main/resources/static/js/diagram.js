function initDiagram() {
    if (window.goSamples) goSamples();  // init for these samples -- you don't need to call this
    var $ = go.GraphObject.make;  // for conciseness in defining templates

    functionDiagram =
        $(go.Diagram, "blockDiagram",  // must name or refer to the DIV HTML element
            {
                layout: $(go.TreeLayout),
                initialContentAlignment: go.Spot.Center,
                allowDrop: true,  // must be true to accept drops from the Palette
                "animationManager.duration": 800, // slightly longer than default (600ms) animation
                "undoManager.isEnabled": true,  // enable undo & redo
                "TextEdited": onTextEdited,
                "ChangedSelection": onSelectionChanged
            });

    // when the document is modified, add a "*" to the title and enable the "Save" button
    functionDiagram.addDiagramListener("Modified", function (e) {
        var idx = document.title.indexOf("*");
        if (functionDiagram.isModified) {
            if (idx < 0) document.title += "*";
        } else {
            if (idx >= 0) document.title = document.title.substr(0, idx);
        }
    });
    var portSize = new go.Size(6, 6);

    // Add a port to the specified side of the selected nodes.
    function addPort() {
        functionDiagram.startTransaction("addPort");
        functionDiagram.selection.each(function (node) {
            // skip any selected Links
            if (!(node instanceof go.Node)) return;
            // compute the next available index number for the side
            var i = 0;
            while (node.findPort('input' + i.toString()) !== node) i++;
            // now this new port name is unique within the whole Node because of the side prefix
            var name = 'input' + i.toString();
            // get the Array of port data to be modified
            var arr = node.data['inputArray'];
            if (arr) {
                // create a new port data object
                var newportdata = {
                    portId: name,
                    portColor: go.Brush.randomColor()
                    // if you add port data properties here, you should copy them in copyPortData above
                };
                // and add it to the Array of port data
                functionDiagram.model.insertArrayItem(arr, -1, newportdata);
            }
        });
        functionDiagram.commitTransaction("addPort");
    }

    // Remove the clicked port from the node.
    // Links to the port will be redrawn to the node's shape.
    function removePort(port) {
        functionDiagram.startTransaction("removePort");
        var pid = port.portId;
        var arr = port.panel.itemArray;
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].portId === pid) {
                functionDiagram.model.removeArrayItem(arr, i);
                break;
            }
        }
        functionDiagram.commitTransaction("removePort");
    }

    var portMenu =  // context menu for each port
        $(go.Adornment, "Vertical",
            $("ContextMenuButton",
                $(go.TextBlock, "Remove input"),
                // in the click event handler, the obj.part is the Adornment; its adornedObject is the port
                {
                    click: function (e, obj) {
                        removePort(obj.part.adornedObject);
                    }
                }));

    var nodeMenu =  // context menu for each Node
        $(go.Adornment, "Vertical",
            $("ContextMenuButton",
                $(go.TextBlock, "Add a new input"),
                {
                    click: function () {
                        addPort();
                    }
                })
        );

    // helper definitions for node templates

    function nodeStyle() {
        return [
            // The Node.location comes from the "loc" property of the node data,
            // converted by the Point.parse static method.
            // If the Node.location is changed, it updates the "loc" property of the node data,
            // converting back using the Point.stringify static method.
            new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
            {
                // the Node.location is at the center of each node
                locationSpot: go.Spot.Center,
                mouseEnter: function (e, obj) {
                    showPorts(obj.part, true);
                },
                mouseLeave: function (e, obj) {
                    showPorts(obj.part, false);
                }
            }
        ];
    }

    // define the Node templates for regular nodes
    var lightText = 'whitesmoke';

    functionDiagram.linkTemplate =
        $(go.Link,
            {
                routing: go.Link.AvoidsNodes,
                curve: go.Link.JumpGap,
                corner: 10,
                reshapable: true,
                toShortLength: 7,
                relinkableFrom: true,
                selectionAdorned: false,
                relinkableTo: true,
                resegmentable: true,
                contextMenu: portMenu
            },
            new go.Binding("points").makeTwoWay(),
            // mark each Shape to get the link geometry with isPanelMain: true
            $(go.Shape, {isPanelMain: true, stroke: "black", strokeWidth: 5}),
            $(go.Shape, {isPanelMain: true, stroke: "gray", strokeWidth: 3}),
            $(go.Shape, {
                isPanelMain: true,
                stroke: "white",
                strokeWidth: 1,
                name: "PIPE",
                strokeDashArray: [10, 10]
            }),
            $(go.Shape, {toArrow: "Triangle", fill: "black", stroke: null})
        );

    functionDiagram.nodeTemplateMap.add("",  // the default category
        $(go.Node, "Table", nodeStyle(),
            {
                doubleClick: function (e, obj) {
                    console.log(obj.data);
                },
                contextMenu: nodeMenu,
                locationObjectName: "BODY",
                locationSpot: go.Spot.Center,
                selectionObjectName: "BODY"
            },
            // the main object is a Panel that surrounds a TextBlock with a rectangular Shape
            $(go.Panel, "Auto", {row: 1, column: 1, stretch: go.GraphObject.Fill, name: "BODY"},
                $(go.Shape, "Rectangle",
                    {fill: "lightgrey", stroke: null, minSize: new go.Size(56, 56)}
                ),
                $(go.Panel, "Table",
                    $(go.RowColumnDefinition,
                        {column: 0, alignment: go.Spot.Left}),
                    $(go.RowColumnDefinition,
                        {column: 2, alignment: go.Spot.Right}),
                    $(go.TextBlock,  // the node title
                        {
                            column: 0, row: 0, columnSpan: 3, alignment: go.Spot.Center,
                            font: "bold 10pt sans-serif", margin: new go.Margin(4, 2),
                            editable: true, isMultiline: false
                        },
                        new go.Binding("text", "text").makeTwoWay()),
                    $(go.TextBlock,  // the node block
                        {
                            column: 0, row: 1, columnSpan: 3, alignment: go.Spot.Center,
                            font: "bold 10pt sans-serif", margin: new go.Margin(4, 2)
                        },
                        new go.Binding("text", "internalType").makeTwoWay()),
                    $(go.Panel, "Horizontal",
                        {column: 2, row: 2, rowSpan: 2},
                        $(go.TextBlock, "Out"),  // "Out" port label
                        $(go.Shape,  // the "Out" port
                            {
                                width: 6,
                                height: 6,
                                portId: "OUT",
                                fromSpot: go.Spot.Right,
                                fromLinkable: true
                            })
                    )
                )
            ),
            // the Panel holding the left port elements, which are themselves Panels,
            // created for each item in the itemArray, bound to data.leftArray
            $(go.Panel, "Vertical",
                new go.Binding("itemArray", "inputArray"),
                {
                    row: 1, column: 0,
                    itemTemplate: $(go.Panel,
                        {
                            _side: "left",  // internal property to make it easier to tell which side it's on
                            fromSpot: go.Spot.Left,
                            toSpot: go.Spot.Left,
                            toMaxLinks: 1,
                            toLinkable: true,
                            cursor: "pointer",
                            contextMenu: portMenu
                        },
                        new go.Binding("portId", "portId"),
                        $(go.Shape, "Rectangle",
                            {
                                stroke: null, strokeWidth: 0,
                                desiredSize: portSize,
                                margin: new go.Margin(0, 0)
                            },
                            new go.Binding("fill", "portColor"))
                    )  // end itemTemplate
                }
            )  // end Vertical Panel
        ));

    functionDiagram.nodeTemplateMap.add("Input",
        $(go.Node, "Spot", nodeStyle(),
            $(go.Panel, "Auto",
                $(go.Panel, "Auto",
                    $(go.Shape, "Rectangle",
                        {minSize: new go.Size(40, 40), fill: "#79C900", stroke: null})
                ),
                $(go.Panel, "Table",
                    $(go.RowColumnDefinition,
                        {column: 0, alignment: go.Spot.Left}),
                    $(go.RowColumnDefinition,
                        {column: 2, alignment: go.Spot.Right}),
                    $(go.TextBlock,  // the node title
                        {
                            column: 0, row: 0, columnSpan: 3, alignment: go.Spot.Center,
                            font: "bold 10pt sans-serif", margin: new go.Margin(4, 2),
                            editable: true, isMultiline: false
                        },
                        new go.Binding("text").makeTwoWay()),
                    $(go.TextBlock,  // the node block
                        {
                            column: 0, row: 1, columnSpan: 3, alignment: go.Spot.Center,
                            font: "bold 10pt sans-serif", margin: new go.Margin(4, 2)
                        },
                        new go.Binding("text", "internalType").makeTwoWay()),
                    $(go.Panel, "Horizontal",
                        {column: 2, row: 2, rowSpan: 2},
                        $(go.TextBlock, "Out"),  // "Out" port label
                        $(go.Shape,  // the "Out" port
                            {
                                width: 6,
                                height: 6,
                                portId: "OUT",
                                fromSpot: go.Spot.Right,
                                fromLinkable: true
                            })
                    )
                )
            )
        )
    );

    functionDiagram.nodeTemplateMap.add("End",
        $(go.Node, "Spot", nodeStyle(),
            $(go.Panel, "Auto",
                $(go.Shape, "Rectangle",
                    {minSize: new go.Size(40, 40), fill: "#DC3C00", stroke: null}),
                $(go.TextBlock, "End",
                    {font: "bold 11pt Helvetica, Arial, sans-serif", stroke: lightText},
                    new go.Binding("text"))
            ),
            $(go.Shape, "Rectangle",
                {
                    width: 6,
                    height: 6,
                    alignment: new go.Spot(0, 0.5),
                    portId: "IN",
                    toSpot: go.Spot.Left,
                    toLinkable: true,
                    toMaxLinks: 1
                }
            )
        ));

    functionDiagram.nodeTemplateMap.add("Comment",
        $(go.Node, "Auto", nodeStyle(),
            $(go.Shape, "File",
                {fill: "#EFFAB4", stroke: null}),
            $(go.TextBlock,
                {
                    margin: 5,
                    maxSize: new go.Size(200, NaN),
                    wrap: go.TextBlock.WrapFit,
                    textAlign: "center",
                    editable: true,
                    font: "bold 12pt Helvetica, Arial, sans-serif",
                    stroke: '#454545'
                },
                new go.Binding("text").makeTwoWay())
            // no ports, because no links are allowed to connect with a comment
        ));

    // temporary links used by LinkingTool and RelinkingTool are also orthogonal:
    functionDiagram.toolManager.linkingTool.temporaryLink.routing = go.Link.Orthogonal;
    functionDiagram.toolManager.relinkingTool.temporaryLink.routing = go.Link.Orthogonal;

    loadDiagram();  // loadDiagram an initial diagram from some JSON text
    loop(); // simulate flow through pipes
    // initialize the Palette that is on the left side of the page
    myPalette =
        $(go.Palette, "blockPalette",  // must name or refer to the DIV HTML element
            {
                "animationManager.duration": 800, // slightly longer than default (600ms) animation
                nodeTemplateMap: functionDiagram.nodeTemplateMap,  // share the templates used by functionDiagram
                model: new go.GraphLinksModel([  // specify the contents of the Palette
                    {category: "Input", text: "Channel"},
                    {text: "Function", inputArray: [{"portColor": "#425e5c", "portId": "left0"}]},
                    {category: "Comment", text: "Comment"}
                ])
            });

}

// Make all ports on a node visible when the mouse is over the node
function showPorts(node, show) {
    var diagram = node.diagram;
    if (!diagram || diagram.isReadOnly || !diagram.allowLink) return;
    node.ports.each(function (port) {
        port.stroke = (show ? "white" : null);
    });
}

function loop() {
    var diagram = functionDiagram;
    setTimeout(function () {
        var oldskips = diagram.skipsUndoManager;
        diagram.skipsUndoManager = true;
        diagram.links.each(function (link) {
            var shape = link.findObject("PIPE");
            if (shape != null) {
                var off = shape.strokeDashOffset;
                off -= 2;
                shape.strokeDashOffset = (off <= 0) ? 20 : off;
            }
        });
        diagram.skipsUndoManager = oldskips;
        loop();
    }, 100);
}

// Allow the user to edit text when a single node is selected
function onSelectionChanged(e) {
    var node = e.diagram.selection.first();
    if (node instanceof go.Node) {
        updateProperties(node.data);
    } else {
        updateProperties(null);
    }
}

function onTextEdited(e) {
    var tb = e.subject;
    if (tb === null || !tb.name) return;
    var node = tb.part;
    if (node instanceof go.Node) {
        updateProperties(node.data);
    }
}

// Update the HTML elements for editing the properties of the currently selected node, if any
function updateProperties(data) {
    if (data === null || data.category === 'End') {
        document.getElementById("propertiesProcessorPanel").style.display = "none";
        document.getElementById("propertiesChannelPanel").style.display = "none";
        document.getElementById("processorId").value = "";
        document.getElementById("processorName").value = "";
        document.getElementById("blockName").value = "";
        document.getElementById("blockComments").value = "";
        document.getElementById("blockEditAnchor").href = "#";
        document.getElementById("blockChannelName").value = "";
        document.getElementById("channelId").value = "";
        document.getElementById("channelName").value = "";
        document.getElementById("blockChannelComments").value = "";
    } else {
        if (data.category === 'Input') {
            document.getElementById("propertiesProcessorPanel").style.display = "none";
            document.getElementById("propertiesChannelPanel").style.display = "block";
            document.getElementById("blockChannelName").value = data.text || "";
            document.getElementById("channelId").value = data.blockId || "";
            document.getElementById("channelName").value = data.internalType || "";
            document.getElementById("blockChannelComments").value = data.comments || "";
        } else if (data.category === 'Comment') {
            document.getElementById("propertiesProcessorPanel").style.display = "none";
            document.getElementById("propertiesChannelPanel").style.display = "none";
        }
        else {
            document.getElementById("propertiesProcessorPanel").style.display = "block";
            document.getElementById("propertiesChannelPanel").style.display = "none";
            document.getElementById("blockName").value = data.text || "";
            document.getElementById("processorId").value = data.blockId || "";
            document.getElementById("blockComments").value = data.comments || "";
            document.getElementById("processorName").value = data.internalType || "";
            document.getElementById("blockEditAnchor").href = "/management/block/" + data.blockId || "#";
        }
    }
}

// Update the data fields when the text is changed
function updateData(text, key) {
    var node = functionDiagram.selection.first();
    // maxSelectionCount = 1, so there can only be one Part in this collection
    var data = node.data;
    if (node instanceof go.Node && data !== null) {
        var model = functionDiagram.model;
        model.startTransaction("modified " + key);
        model.setDataProperty(data, key, text);
        model.commitTransaction("modified " + key);
    }
}

// Show the diagram's model in JSON format that the user may edit
function saveDiagram() {
    document.getElementById("diagramModel").value = functionDiagram.model.toJson();
    functionDiagram.isModified = false;
    return true;
}
function loadDiagram() {
    functionDiagram.model = go.Model.fromJson(document.getElementById("diagramModel").value);
}

// add an SVG rendering of the diagram at the end of this page
function makeSVG() {
    var svg = functionDiagram.makeSvg({
        scale: 1.0
    });
    svg.style.border = "1px solid black";
    var obj = document.getElementById("SVGArea");
    obj.appendChild(svg);
    if (obj.children.length > 0) {
        obj.replaceChild(svg, obj.children[0]);
    }
}
