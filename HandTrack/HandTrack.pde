// Daniel Shiffman and Thomas Sanchez Lengeling
// Tracking the average location beyond a given depth threshold
// Thanks to Dan O'Sullivan

// https://github.com/shiffman/OpenKinect-for-Processing
// http://shiffman.net/p5/kinect/

import org.openkinect.processing.*;
// The kinect stuff is happening in another class
KinectTracker tracker;
data_saver saver;
PrintWriter output;
dtree dt;
gbm_test g;
Wintrakr w;

PImage img;
PImage rock_img;
PImage therock_img;
PImage paper_img;
PImage scissors_img;
PFont font;

int centercount = 0;
int pos_len = 0;

int wins = 0;
int losses = 0;
int games = 0;
int errors = 0;

PImage cur_player_img;
PImage cur_comp_img;
String display_text;

boolean wait = false;
void setup() {
  size(640+500, 520);

  font = loadFont("BebasNeueBold-72.vlw");
  img = loadImage("background.png");
  rock_img = loadImage("rock.png");
  therock_img = loadImage("therock.jpg");
  paper_img = loadImage("paper.png");
  scissors_img = loadImage("scissors.png");
  cur_player_img = loadImage("blank.png");
  cur_comp_img = loadImage("blank.png");

  dt = new dtree();
  tracker = new KinectTracker(this);
  saver = new data_saver();
  w = new Wintrakr();
  output = createWriter("positions.txt");
  g = new gbm_test();

  int dispRand = (int)random(3);
  if(dispRand == 0){
    display_text = "Play scissors (trust me)!";
  }
  else if(dispRand == 1){
    display_text = "Play paper (trust me)!";
  }
  else{
    display_text = "Play rock (trust me)!";
  }
  
}


void draw() {
    
  textFont(font, 12);
  if(wait){
    wait = false;
    delay(500);
  }
  background(255);

  // Show the image
  tracker.display();

  // Display some info
  int a = tracker.getArea();
  int p = tracker.getPerimeter();
  int l = tracker.getHorz();
  int h = tracker.getVert();
  int[] center = tracker.getCenter();
  
  fill(0,0,255);
  ellipse(center[0],center[1],5,5);
  
  fill(0);
  textSize(20);
  text("Per: " + p + "    " + "Area: " + a + "    " +  "Length: " + l + "    " + "Height: " + h + "    ", 10, 500);
  text("WINS: " + wins + "    " + "LOSSES: " + losses + "    " +  "GAMES: " + games + "    " + "ERRORS: " + errors + "    ", 10, 515);
  
  /*String s = " " + pos_len + " ";
  fill(0,255,0);
  textSize(75);
  text(s,25,100);*/
  
    if(center[0] + 20 > tracker.getWidth()/2 && center[0] - 20 < tracker.getWidth()){
      if(center[1] + 20 > tracker.getHeight()/2 && center[1] - 20 < tracker.getHeight()){
        fill(255,0,0);
        ellipse(tracker.getWidth()/2, tracker.getHeight()/2,20,20);
    
        centercount++;
      }
      else{
        centercount = 0;
      }
    }
    else{
        centercount = 0;
    }
    if(centercount > 30){
      playRPS();
      wait = true;
      centercount = 0;
    }

  //final steps in draw
  image(img, 550,0,500,400);
  image(cur_comp_img,840,200,200,200);
  image(cur_player_img,570,200,200,200);
  fill(0);
  textSize(72);
  text(wins,670,68);
  text(losses,970,68);
  textSize(48);
  text(display_text,700,490);
  
}


void keyPressed() {
  if (key == ' '){
    
    playRPS();
    wait = true;
  }
  
  if (key == 's'){
    save_pos();
  }
  
  if (key == 'x'){
    output.flush();
    output.close();
    exit();
  }
  
  if (key == 'w'){
    errors++;
    wins--;
    games--;
  }
  
  if (key == 'l'){
    errors++;
    losses--;
    games--;
  }
  
  if(key == 'q'){
    saver.save_all(w);
  }

  

}

void save_pos(){
  int p = tracker.getPerimeter();
  int a = tracker.getArea();
  int l = tracker.getHorz();
  int h = tracker.getVert();
  output.println(p + " " + a + " " + l + " " + h);
  pos_len++;


}

void playRPS() {
  

  int p = tracker.getPerimeter();
  int a = tracker.getArea();
  int l = tracker.getHorz();
  int h = tracker.getVert();
  
  int userchoice = -1;
  fill(0,255,0);
  textSize(75);
  /*if(l < 200){
    userchoice = 0;
    text("ROCK",25,100);
  }
  else if(p > 1300){
    userchoice = 2;
    text("SCISSORS",25,100);
  }
  else if(a > 20000){
    userchoice = 1;
    text("PAPER",25,100);
  }*/
  //String move = dt.move(p, a, l, h);
  double[] data = {p, a, l, h};
  double[] pred = new double[4];

  double[] mv = g.score0(data, pred);
  String move = "";
  if(mv[0] == 0){
    cur_player_img = paper_img;
    move = "PAPER";
  }
  if(mv[0] == 1){
    move = "ROCK";
    cur_player_img = therock_img;
  }
  if(mv[0] == 2){
    move = "SCISSORS";
    cur_player_img = scissors_img;
  }

  text(move,25,100);
  if(move == "ROCK"){
    userchoice = 0;
  }
  else if(move == "PAPER"){
    userchoice = 1;
  }
  else if(move == "SCISSORS"){
    userchoice = 2;
  }
  fill(255,0,0);
  //text("VS",200,100);
  
  //int compchoice = int(random(3));
  int compchoice = saver.choose();
  println(compchoice);
  if(compchoice == 0){
    //int therockrand = (int)random(10);
    cur_comp_img = therock_img;
  }
  if(compchoice == 1){
    cur_comp_img = paper_img;
  }
  if(compchoice == 2){
    cur_comp_img = scissors_img;
  }
  fill(0,0,255);
  textSize(75);
  if(compchoice == 0){
    //text("ROCK",300,100);
  }
  else if(compchoice == 2){
    //text("SCISSORS",300,100);
  }
  else if(compchoice == 1){
    //text("PAPER",300,100);
  }
  
 
  
  char res = 'x';
  fill(255,0,255);
  textSize(150);
  text("hello",400,700);
  if(userchoice == -1){
    text("ERROR",400,700);
    w.update('e');
    errors++;
  }
  if(userchoice == compchoice){
    display_text = "TIE";
    games++;
    res = 't';
  }
  if(userchoice == 0 && compchoice == 1){
    display_text = "YOU LOSE :[";
    losses++;
    games++;
    res = 'l';
  }
  if(userchoice == 0 && compchoice == 2){
    display_text = "YOU WIN!";
    wins++;
    games++;
    res = 'w';
  }
  if(userchoice == 1 && compchoice == 0){
    display_text = "YOU WIN!";
    wins++;
    games++;
    res = 'w';
  }
  if(userchoice == 1 && compchoice == 2){
    display_text = "YOU LOSE :[";
    losses++;
    games++;
    res = 'l';
  }
  if(userchoice == 2 && compchoice == 0){
    display_text = "YOU LOSE :[";
    losses++;
    games++;
    res = 'l';
  }
  if(userchoice == 2 && compchoice == 1){
    display_text = "YOU WIN!";
    wins++;
    games++;
    res = 'w';
  }
  
  if(userchoice != -1 && res != 'x'){
     char usr = 'x';
     if(userchoice == 0){ usr = 'r';}
     if(userchoice == 1){ usr = 'p';}
     if(userchoice == 2){ usr = 's';}
     saver.save(usr,res);
     w.update(res);
  }
  
  return;
}
