let Engine = Matter.Engine,
    Render = Matter.Render,
    World = Matter.World,
    Bodies = Matter.Bodies,
    Body = Matter.Body,
    Events = Matter.Events,
    Mouse = Matter.Mouse,
    MouseConstraint = Matter.MouseConstraint;

// create an engine
let engine = Engine.create();

$(document).ready(() => {
    // create a renderer
    var w = window.innerWidth;
    var h = window.innerHeight;

    let render = Render.create({
        element: document.body,
        engine: engine,
        options: {
            wireframes: false,
            height: h,
            width: w
        }
    });

    var jarHeight = h * 0.8;
    var jarwidth = jarHeight * 0.5;
    let jarParts = {
        bottom: Bodies.rectangle(w / 2, h - 50, jarwidth, 20, {
            isStatic: true,
            chamfer: {radius: 5},
            render: {
                fillStyle: 'grey',
                strokeStyle: 'white'
            }
        }),
        right: Bodies.rectangle((w / 2) - (jarwidth / 2), (h - 50) - (jarHeight / 2), 20, jarHeight, {
            isStatic: true,
            chamfer: {radius: 5},
            render: {
                fillStyle: 'grey',
                strokeStyle: 'white'
            }
        }),
        left: Bodies.rectangle((w / 2) + (jarwidth / 2), (h - 50) - (jarHeight / 2), 20, jarHeight, {
            isStatic: true,
            chamfer: {radius: 5},
            render: {
                fillStyle: 'grey',
                strokeStyle: 'white'
            }
        })
    };

    let candyJar = [
        jarParts.bottom,
        jarParts.left,
        jarParts.right
    ];

    World.add(engine.world, candyJar);

    // add mouse control
    let mouse = Mouse.create(render.canvas),
        mouseConstraint = MouseConstraint.create(engine, {
            mouse: mouse,
            constraint: {
                stiffness: 0.2,
                render: {
                    visible: false
                }
            }
        });

    World.add(engine.world, mouseConstraint);

    render.mouse = mouse;

    Events.on(engine, "afterUpdate", (...args) => {

    });

    // run the engine
    Engine.run(engine);

    // run the renderer
    Render.run(render);
});

function drawCandy(allCandy) {
    let bodies = [];
    $.each(allCandy, function (i, val) {
        let options = {};
        let name = val.name.toLowerCase().replace(" ", "");
        if (fileExists('images/' + name + '.jpg')) {
            options = {
                density: 0.00001,
                slop: 0,
                render: {
                    fillStyle: "#C44D58",
                    sprite: {
                        texture: 'images/' + name + '.jpg'
                    }
                },
                torque: (Math.random() - .5) / 10
            };
        } else {
            options = {
                density: 0.00001,
                slop: 0,
                render: {
                    fillStyle: "#C44D58",
                    sprite: {
                        texture: 'images/blank.jpg'
                    }
                },
                torque: (Math.random() - .5) / 10
            }
        }
        for (let i = 0; i < val.quantity; i++) {
            setTimeout(function () {
                World.add(engine.world,
                    Bodies.rectangle(window.innerWidth / 2, 0, 100, 40, options, 8)
                );
            }, 1000);
        }
    });
    $.each(bodies, function (i, val) {

    });
}

function toRadians(angle) {
    return angle * (Math.PI / 180);
}

function fileExists(url) {
    if (url) {
        let req = new XMLHttpRequest();
        req.open('GET', url, false);
        req.send();
        return req.status === 200;
    } else {
        return false;
    }
}