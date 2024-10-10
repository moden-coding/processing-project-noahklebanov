import processing.core.*;

public class App extends PApplet {
    public static void main(String[] args) {
        PApplet.main("App");
    }

   //insert image variables
    PImage img;
    PImage imgButton;

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
    float carMotion = 20;

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
    float dashMotion = 0;
    float dashSpacing = 80;

    // obstacle variables
    float obstacleSpacing = random(350, 450);

    float obstacleX1 = random(250, 450);
    float obstacleY1 = 0;
    float obstacleWidth1 = random(50, 100);
    float obstacleHeight1 = 20;

    float obstacleX2 = random(250, 450);
    float obstacleY2 = obstacleSpacing*-1;
    float obstacleWidth2 = random(50, 100);
    float obstacleHeight2 = 20;

    //collision text variables
    String collisionMessage = "";
    String restartMessage = "";
    String returnHomeMessage = "";

    //resart and start variables
    boolean gameRunning=false;
    boolean collisionHappened=false;
    
    //score variables
    int scoreCount = 0;
    boolean passedObstacle = false;


    public void settings() {
        size(800, 600);
    }
    
    public void setup() {
        frameRate(60);

        img = loadImage("redCar.png");
        imgButton = loadImage("playButton.png");
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
            
            if(obstacleCollisionBoolean(obstacleX1, obstacleY1, obstacleWidth1, obstacleHeight1) || 
                obstacleCollisionBoolean(obstacleX2, obstacleY2, obstacleWidth2, obstacleHeight2)){
                collisionHappened();
            }
    
            score();

            if(increaseSpeed()){
                speedMessage();
            }


            
        }    
    }

    public void dashMotion() { //could be improved so the road dashes move in the same way as the obstacles but would require a different approach which would mess up other peices of my code
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
            float obstacleSpacing = random(350, 450);
            obstacleY1 = obstacleY2-obstacleSpacing;
            obstacleX1 = random(250, 450);
            obstacleWidth1 = random(50, 100); 
        }

        rect(obstacleX2, obstacleY2, obstacleWidth2, obstacleHeight2);
        obstacleY2 += dashMotion;
        if (obstacleY2 > height) {
            float obstacleSpacing = random(350, 450);
            obstacleY2=obstacleY1-obstacleSpacing;
            obstacleX2 = random(250, 450);
            obstacleWidth2 = random(50, 100);
        } 
        
    }
    
    /*public void obstacleCollision(){ //removed and replaced by two other methods(kept just in case)
        if (carX < obstacleX1 + obstacleWidth1 && 
        carX + carWidth > obstacleX1 &&
        carY < obstacleY1 + obstacleHeight1 && 
        carY + carLength > obstacleY1) {

            dashMotion=0;
            background(255);
            gameRunning=false;
            collisionHappened=true;
            collisionMessage = "GAME OVER";
            restartMessage = "Press enter to retry";
            returnHomeMessage = "Press space to return home";
            
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
            returnHomeMessage = "Press space to return home";
            
        }
    }*/

    public boolean obstacleCollisionBoolean(float obstacleX, float obstacleY, float obstacleWidth, float obstacleHeight){
        if (carX < obstacleX + obstacleWidth && 
        carX + carWidth > obstacleX &&
        carY < obstacleY + obstacleHeight && 
        carY + carLength > obstacleY){
            return true;
        }else{
            return false;
        }
    }

    public void collisionHappened(){ //defines what happens when obstacleCollisionBoolean is true
        dashMotion=0;
        background(255);
        gameRunning=false;
        collisionHappened=true;
        collisionMessage = "GAME OVER";
        restartMessage = "Press enter to continue";
        returnHomeMessage = "Press space to return home";
        fill(0);
        textSize(100); 
        text(collisionMessage, 30, 300);
        textSize(20);
        text(restartMessage, 30,400);
        text(returnHomeMessage,30,430);
    }

    public void gameInstructions(){ //makes the starting screen
        background(255);
        fill(255,0,0);
        image(imgButton,325,275,150,75);
        textSize(22);
        fill(0);
        text("Instructions:",10,20);
        text("Goal",10,500);
        textSize(15);
        //instruction variables
        String keyInstructionsLeft = "Press left arrow to move car left";
        String keyInstructionsRight = "Press right arrow to move car right";
        String keyInstructionsUp = "Press up arrow to accelerate the car";
        String keyInstructionsDown = "Press down arrow to deccelerate the car";
        String goalOfGame = "move the car left and right to avoid the gray obstacles. If you want more of a challenge increase the cars speed with the up";
        String goalOfGameTwo = "arrow, if the game is going too fast use the down arrow to slow down. To keep track of how many obstacles you have passed,";
        String goalOfGameThree = "a score counter is in the top right corner of the screen which increases by one every time an obstacle is passed. Every time";
        String goalOfGameFour = "you reach a certain score, the car speed gets faster, so the higher your score the faster the car goes";
        text(keyInstructionsRight,10,40);
        text(keyInstructionsLeft,10,60);
        text(keyInstructionsUp,10,80);
        text(keyInstructionsDown,10,100);
        text(goalOfGame,10,520);
        text(goalOfGameTwo,10,540);
        text(goalOfGameThree,10,560);
        text(goalOfGameFour,10,580);
    }

    public void score(){ //determines when score increases
        //System.out.println("obstacleY1: " + obstacleY1 + " obstacleY2: " + obstacleY2 + " carY: " + carY + " Score: " + scoreCount); //debug for faulty score
        if(obstacleY1>carY || obstacleY2>carY){
            if (!passedObstacle) {
                scoreCount++;
                passedObstacle = true;
            }
        }
        else {
            passedObstacle = false;
        }

        textSize(25);
        fill(0);
        text("Score:" + scoreCount, 675,35);
    }

    public boolean increaseSpeed(){ //determines when to increase speed as a function of score and how much to increase speed by
        if(scoreCount>=10 && scoreCount<20 && dashMotion<=6){
            return true;
        }else if(scoreCount>=20 && scoreCount<35 && dashMotion<=8){
            return true;
        }else if(scoreCount>=35 && scoreCount<50 && dashMotion<=10){
            return true;
        }else{
            return false;
        }
    }

    public void speedMessage(){
        dashMotion+=0.05;
        if(gameRunning && !collisionHappened){
            textSize(40);
            text("Increasing Speed!",325,30);
        }
    }

    public void keyPressed() {
        if (keyCode == RIGHT && carX < 550-carWidth) {
            carX += carMotion;
        }
        if (keyCode == LEFT && carX > 250) {
            carX -= carMotion;
        }
        if (keyCode == UP && dashMotion<=20) {
            dashMotion += 0.25;
        }
        if (keyCode == DOWN && dashMotion > 0) {
            dashMotion -= 0.25;
        }
        if(keyCode == ENTER && !gameRunning && collisionHappened){ //code to reset game
            collisionMessage="";
            restartMessage="";
            returnHomeMessage="";
            obstacleY2 = obstacleSpacing * -1;
            obstacleY1 = 0;
            dashMotion=4;
            scoreCount=0;
            gameRunning=true;
    
        }

        if(keyCode == ' ' && !gameRunning){ //code to return to home screen
            collisionHappened=false;
            collisionMessage="";
            restartMessage="";
            returnHomeMessage="";
            obstacleY2 = obstacleSpacing * -1;
            obstacleY1 = 0;
            scoreCount=0;

        }
    }

    public void mousePressed() { //used to determine if play button is pressed
        if(325<mouseX && mouseX<475 && 262.5<mouseY && mouseY<337.5 && !gameRunning){
            gameRunning=true;
            dashMotion=4;
        }
    }
}
