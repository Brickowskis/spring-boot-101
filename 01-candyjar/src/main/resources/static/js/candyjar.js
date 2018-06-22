let Engine = Matter.Engine,
    Render = Matter.Render,
    World = Matter.World,
    Bodies = Matter.Bodies,
    Body = Matter.Body,
    Events = Matter.Events;

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

    var jarHeight = h*0.8;
    var jarwidth = jarHeight*0.5;
    let jarParts = {
        bottom: Bodies.rectangle(w/2, h-50, jarwidth, 20, {
            isStatic: true,
            chamfer: {radius: 5},
            render: {
                fillStyle: 'grey',
                strokeStyle: 'white'
            }
        }),
        right: Bodies.rectangle((w/2)-(jarwidth/2), (h-50)-(jarHeight/2), 20, jarHeight, {
            isStatic: true,
            chamfer: {radius: 5},
            render: {
                fillStyle: 'grey',
                strokeStyle: 'white'
            }
        }),
        left: Bodies.rectangle((w/2)+(jarwidth/2), (h-50)-(jarHeight/2), 20, jarHeight, {
            isStatic: true,
            chamfer: {radius: 5},
            render: {
                fillStyle: 'grey',
                strokeStyle: 'white'
            }
        })
    };

    let tipCup = [
        jarParts.bottom,
        jarParts.left,
        jarParts.right
    ];

    World.add(engine.world, tipCup);

    //Body.rotate(jarParts.right, toRadians(5));
    //Body.rotate(jarParts.left, toRadians(-5));

    Events.on(engine, "afterUpdate", (...args) => {

    });

    // run the engine
    Engine.run(engine);

    // run the renderer
    Render.run(render);
});

function drawCandy(allCandy) {
    let bodies = [];
    $.each(allCandy, function(i, val) {
        for(let i = 0; i < val.quantity; i++) {
            setTimeout(function() {
                World.add(engine.world,
                    Bodies.rectangle(window.innerWidth/2, 0, 100, 40, {
                        density: 0.00001,
                        slop: 0,
                        render: {
                            fillStyle: "#C44D58",
                            sprite:{
                                texture: 'images/'+val.name+'.jpg'
                            }
                        },
                        torque: (Math.random()-.5)/10
                    }, 8)
                );
            }, 1000);
        }
    });
    $.each(bodies, function(i, val) {

    });
}

function toRadians(angle) {
    return angle * (Math.PI / 180);
}