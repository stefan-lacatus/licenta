function init_diagram() {
    if (window.goSamples) goSamples();  // init for these samples -- you don't need to call this
    var $ = go.GraphObject.make;  // for conciseness in defining templates

    myDiagram =
        $(go.Diagram, "blockDiagram",  // must name or refer to the DIV HTML element
            {
                layout: $(go.TreeLayout),
                initialContentAlignment: go.Spot.Center,
                allowDrop: true,  // must be true to accept drops from the Palette
                "animationManager.duration": 800, // slightly longer than default (600ms) animation
                "undoManager.isEnabled": true  // enable undo & redo
            });

    // when the document is modified, add a "*" to the title and enable the "Save" button
    myDiagram.addDiagramListener("Modified", function (e) {
        var button = document.getElementById("SaveButton");
        if (button) button.disabled = !myDiagram.isModified;
        var idx = document.title.indexOf("*");
        if (myDiagram.isModified) {
            if (idx < 0) document.title += "*";
        } else {
            if (idx >= 0) document.title = document.title.substr(0, idx);
        }
    });

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
                //isShadowed: true,
                //shadowColor: "#888",
                // handle mouse enter/leave events to show/hide the ports
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

    myDiagram.linkTemplate =
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
                resegmentable: true
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

    myDiagram.nodeTemplateMap.add("",  // the default category
        $(go.Node, "Spot", nodeStyle(),
            {
                doubleClick: function (e, obj) {
                    console.log(obj.data);
                }
            },
            // the main object is a Panel that surrounds a TextBlock with a rectangular Shape
            $(go.Panel, "Auto",
                $(go.Shape, "Rectangle",
                    {fill: "lightgrey", stroke: null},
                    new go.Binding("figure", "figure")),
                $(go.Panel, "Table",
                    $(go.RowColumnDefinition,
                        {column: 0, alignment: go.Spot.Left}),
                    $(go.RowColumnDefinition,
                        {column: 2, alignment: go.Spot.Right}),
                    $(go.TextBlock,  // the node title
                        {
                            column: 0, row: 0, columnSpan: 3, alignment: go.Spot.Center,
                            font: "bold 10pt sans-serif", margin: new go.Margin(4, 2)
                        },
                        new go.Binding("text").makeTwoWay()),
                    $(go.Panel, "Horizontal",
                        {column: 0, row: 1},
                        $(go.Shape,  // the "IN" port
                            {
                                width: 6,
                                height: 6,
                                portId: "IN",
                                toSpot: go.Spot.Left,
                                toLinkable: true
                            }),
                        $(go.TextBlock, "Inputs")  // "Inputs" port label
                    ),
                    $(go.Panel, "Horizontal",
                        {column: 2, row: 1, rowSpan: 2},
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
        ));

    myDiagram.nodeTemplateMap.add("Start",
        $(go.Node, "Spot", nodeStyle(),
            $(go.Panel, "Auto",
                $(go.Shape, "Rectangle",
                    {minSize: new go.Size(40, 40), fill: "#79C900", stroke: null}),
                $(go.TextBlock, "Start",
                    {font: "bold 11pt Helvetica, Arial, sans-serif", stroke: lightText},
                    new go.Binding("text"))
            ),
            $(go.Shape, "Rectangle",
                {
                    width: 6,
                    height: 6,
                    alignment: new go.Spot(1, 0.5),
                    portId: "OUT",
                    fromSpot: go.Spot.Right,
                    fromLinkable: true
                }
            )
        ));

    myDiagram.nodeTemplateMap.add("End",
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
                    toLinkable: true
                }
            )
        ));

    myDiagram.nodeTemplateMap.add("Comment",
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
    myDiagram.toolManager.linkingTool.temporaryLink.routing = go.Link.Orthogonal;
    myDiagram.toolManager.relinkingTool.temporaryLink.routing = go.Link.Orthogonal;

    loadDiagram();  // loadDiagram an initial diagram from some JSON text
    loop(); // simulate flow through pipes
    // initialize the Palette that is on the left side of the page
    myPalette =
        $(go.Palette, "blockPalette",  // must name or refer to the DIV HTML element
            {
                "animationManager.duration": 800, // slightly longer than default (600ms) animation
                nodeTemplateMap: myDiagram.nodeTemplateMap,  // share the templates used by myDiagram
                model: new go.GraphLinksModel([  // specify the contents of the Palette
                    {text: "Step"},
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
    var diagram = myDiagram;
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


// Show the diagram's model in JSON format that the user may edit
function saveDiagram() {
    document.getElementById("mySavedModel").value = myDiagram.model.toJson();
    myDiagram.isModified = false;
    return true;
}
function loadDiagram() {
    myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
}

// add an SVG rendering of the diagram at the end of this page
function makeSVG() {
    var svg = myDiagram.makeSvg({
        scale: 1.0
    });
    svg.style.border = "1px solid black";
    var obj = document.getElementById("SVGArea");
    obj.appendChild(svg);
    if (obj.children.length > 0) {
        obj.replaceChild(svg, obj.children[0]);
    }
}
