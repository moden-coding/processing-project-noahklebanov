import processing.core.*;

public class App extends PApplet {
    public static void main(String[] args) {
        PApplet.main("App");
    }

    PImage img;

    // Colors
    int grassColor = color(130, 180, 50);
    int carColorRed = color(250, 50, 50);
    int roadColorGray = color(200);
    float roadEdgeBlack = color(0);
    int dashColorYellow = color(250, 230, 0);
    int obstacleColor = color(50);

    // car variables
    float carWidth = 70;
    float carLength = 140;
    float carX = 400-carWidth/2;
    float carY = 600 - carLength - 25;
    float carMotion = 15;// (150 works for only two positions)

    // stationary road variables
    float roadX = 250;
    float roadY = -10;
    float roadWidth = 300;
    float roadHeight = 1000;

    // moving road line
    float dashX = 395;
    float dashY = 0;
    float dashWidth = 10;
    float dashHeight = 20;
    float dashMotion = 4;
    float dashSpacing = 80;

    // obstacle variables
    float obstacleSpacing = random(300, 450);

    float obstacleX1 = random(250, 425);
    float obstacleY1 = 0;
    float obstacleWidth1 = random(50, 100);
    float obstacleHeight1 = 20;

    float obstacleX2 = random(250, 425);
    float obstacleY2 = obstacleSpacing * -1;
    float obstacleWidth2 = random(50, 100);
    float obstacleHeight2 = 20;

    //collision variables
    String collisionMessage = "";
    String restartMessage = "";

    //resart and start variables
    boolean gameRunning=false;
    boolean collisionHappened=false;

    //instruction variables
    String keyInstructionsLeft = "Press left arrow to move car left";
    String keyInstructionsRight = "Press right arrow to move car right";
    String keyInstructionsUp = "Press up arrow to accelerate the car";
    String keyInstructionsDown = "Press down arrow to deccelerate the car";


    public void settings() {
        size(800, 600);
    }
    
    public void setup() {
        frameRate(60);
        img = loadImage("redCar.png");
    }




    public void draw() {
        if(!gameRunning && !collisionHappened){
            gameInstructions();
        }
        else if(gameRunning){
            background(grassColor);

            fill(roadColorGray);
            strokeWeight(5);
            stroke(roadEdgeBlack);
            rect(roadX, roadY, roadWidth, roadHeight);

            noStroke();
            fill(dashColorYellow);
            dashMotion();

            fill(obstacleColor);
            obstacleMotion();

            image(img, carX, carY, carWidth, carLength);

            
            obstacleCollsion();
            fill(0);
            textSize(100); 
            text(collisionMessage, 30, 300);
            textSize(20);
            text(restartMessage, 30,400);

        }    
    }


    public void dashMotion() {
        for (int i = 0; i < 8; i++) {
            rect(dashX, dashY + i * dashSpacing, dashWidth, dashHeight);
        }
        dashY += dashMotion;

        if (dashY >= dashSpacing) {
            dashY = 0;
        }
    }

    public void obstacleMotion() {
        rect(obstacleX1, obstacleY1, obstacleWidth1, obstacleHeight1);
        obstacleY1 += dashMotion;
        if (obstacleY1 > height) {
            obstacleY1 = 0;
            obstacleX1 = random(250, 425);
            obstacleWidth1 = random(50, 100); 
        }

        rect(obstacleX2, obstacleY2, obstacleWidth2, obstacleHeight2);
        obstacleY2 += dashMotion;
        if (obstacleY2 > height) {
            float obstacleSpacing = random(250, 400);
            obstacleY2 = obstacleY1 - obstacleSpacing;
            obstacleX2 = random(250, 425);
            obstacleWidth2 = random(50, 100);
        }
        
    }




    public void obstacleCollsion(){
        if (carX < obstacleX1 + obstacleWidth1 && 
        carX + carWidth > obstacleX1 &&
        carY < obstacleY1 + obstacleHeight1 && 
        carY + carLength > obstacleY1) {
            dashMotion=0;
            background(255);
            gameRunning=false;
            collisionHappened=true;
            collisionMessage = "GAME OVER";
            restartMessage = "Press enter to continue";
            
        }

        if (carX < obstacleX2 + obstacleWidth2 && 
        carX + carWidth > obstacleX2 &&
        carY < obstacleY2 + obstacleHeight2 && 
        carY + carLength > obstacleY2) {
            dashMotion=0;
            background(255);
            gameRunning=false;
            collisionHappened=true;
            collisionMessage = "GAME OVER";
            restartMessage = "Press enter to continue";
            
        }
    }

    public void gameInstructions(){
        background(255);
        fill(255,0,0);
        rect(350,275,100,75);//playbutton
        textSize(15);
        fill(0);
        text(keyInstructionsRight,10,20);
        text(keyInstructionsLeft,10,40);
        text(keyInstructionsUp,10,60);
        text(keyInstructionsDown,10,80);

    }


    public void keyPressed() {
        if (keyCode == RIGHT && carX < 550-carWidth) {
            carX += carMotion;
        }
        if (keyCode == LEFT && carX > 250) {
            carX -= carMotion;
        }
        if (keyCode == UP && dashMotion <= 20) {
            dashMotion += 0.25;
        }
        if (keyCode == DOWN && dashMotion > 0) {
            dashMotion -= 0.25;
        }
        if(keyCode == ENTER && !gameRunning){
            collisionMessage="";
            restartMessage="";
            obstacleY2 = obstacleSpacing * -1;
            obstacleY1 = 0;
            dashMotion=4;
            gameRunning=true;
    
        }
    }

    public void mousePressed() {
        if(350<mouseX && mouseX<450 && 262.5<mouseY && mouseY<337.5){
            gameRunning=true;
        }
    }

}
