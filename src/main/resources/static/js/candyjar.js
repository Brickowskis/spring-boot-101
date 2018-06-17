$(document).ready(() => {

    let Engine = Matter.Engine,
        Render = Matter.Render,
        World = Matter.World,
        Bodies = Matter.Bodies,
        Body = Matter.Body,
        Events = Matter.Events;

    // create an engine
    let engine = Engine.create();

    // create a renderer
    let render = Render.create({
        element: document.body,
        engine: engine
    });

    let jarParts = {
        bottom: Bodies.rectangle(400, 580, 170, 20, {
            isStatic: true,
            render: {
                fillStyle: 'orange',
                strokeStyle: 'black'
            }
        }),
        right: Bodies.rectangle(475, 490, 20, 200, {isStatic: true, chamfer: {radius: 5}}),
        left: Bodies.rectangle(325, 490, 20, 200, {isStatic: true, chamfer: {radius: 5}})
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


    setInterval(() => {
        World.add(engine.world,
            Bodies.circle(455, 100, 10, {
                density: 0.000000001,
                frictionAir: 0.00001,
                restitution: 0.6,
                friction: -0.05,
                slop: 0
            }, 8)
        );
    }, 500);

    setInterval(() => {
        World.add(engine.world,
            Bodies.circle(430, 100, 20, {
                density: 100000,
                slop: 0
            }, 32)
        );
    }, 10000);

    setInterval(() => {
        World.add(engine.world,
            Bodies.circle(440, 50, 30, {
                density: 1000000000000,
                slop: 0
            }, 32)
        );
    }, 30000);


});


function toRadians(angle) {
    return angle * (Math.PI / 180);
}