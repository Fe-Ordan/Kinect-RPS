import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import org.openkinect.processing.*; 

import water.util.*; 
import hex.genmodel.tools.*; 
import hex.*; 
import hex.genmodel.annotations.*; 
import hex.genmodel.easy.*; 
import water.genmodel.*; 
import hex.genmodel.easy.prediction.*; 
import hex.genmodel.*; 
import hex.genmodel.easy.exception.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class HandTrack extends PApplet {

// Daniel Shiffman and Thomas Sanchez Lengeling
// Tracking the average location beyond a given depth threshold
// Thanks to Dan O'Sullivan

// https://github.com/shiffman/OpenKinect-for-Processing
// http://shiffman.net/p5/kinect/


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
public void setup() {
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


public void draw() {
    
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


public void keyPressed() {
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

public void save_pos(){
  int p = tracker.getPerimeter();
  int a = tracker.getArea();
  int l = tracker.getHorz();
  int h = tracker.getVert();
  output.println(p + " " + a + " " + l + " " + h);
  pos_len++;


}

public void playRPS() {
  

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
class KinectTracker {

  
  int area = 0;
  int perimeter = 0;
  int vert = 0;
  int horz = 0;
  
  int[] center = {0,0};
  // Depth data
  int[] depth;

  // What we'll show the user
  PImage display;
  
  //Kinect2 class
  Kinect2 kinect2;
  
  KinectTracker(PApplet pa) {
    
    //enable Kinect2
    kinect2 = new Kinect2(pa);
    kinect2.initDepth();
    kinect2.initDevice();
    
    // Make a blank image
    display = createImage(kinect2.depthWidth, kinect2.depthHeight, RGB);
    
  }
  
  public void display() {
    depth = kinect2.getRawDepth();
    PImage img = kinect2.getDepthImage();

    // Being overly cautious here
    if (depth == null || img == null) return;

    // Going to rewrite the depth image to show which pixels are in threshold
    // A lot of this is redundant, but this is just for demonstration purposes
    display.loadPixels();
    
    int[][] grid = new int[kinect2.depthWidth][kinect2.depthHeight];
    for (int x = 0; x < kinect2.depthWidth; x++) {
      for (int y = 0; y < kinect2.depthHeight; y++) {
        // mirroring image
        int offset = (kinect2.depthWidth - x - 1) + y * kinect2.depthWidth;
        // Raw depth
        int rawDepth = depth[offset];
        int pix = x + y*display.width;
        if (rawDepth > 0) {
          // A red color instead
          grid[x][y] = 1;
          display.pixels[pix] = color(255, 255, 255);
        } else {
          grid[x][y] = 0;
          display.pixels[pix] = color(0,0,0);
        }
        
      }
    }
    area = 0;
    perimeter = 0;
    int leftmost = -1;
    int rightmost = -1;
    int topmost = -1;
    int bottommost = -1;
    
    int[] heights = new int[kinect2.depthWidth];
    for (int x = 0; x < kinect2.depthWidth; x++) {
      int localtopmost = -1;
      int localbottommost = -1;
      for (int y = 0; y < kinect2.depthHeight; y++) {
        int pix = x + y*display.width;
        int p = grid[x][y];
        
        if(x > kinect2.depthWidth*.2f && x < kinect2.depthWidth * .8f && y > kinect2.depthHeight*.3f && y < kinect2.depthHeight * .7f){
          if(p == 0){
            //display.pixels[pix] = color(0,180,0);
            //area++;
            
            if(x > rightmost || rightmost == -1)
            {
              rightmost = x;
            }
            if(x < leftmost || leftmost == -1){
              leftmost = x;
            }
            if(y > topmost || topmost == -1){
              topmost = y;
            }
            if(y < bottommost || bottommost == -1){
              bottommost = y;
            }
            
            if(y > localtopmost || localtopmost == -1){
              localtopmost = y;
            }
            if(y < localbottommost || localbottommost == -1){
              localbottommost = y;
            }
            
          }
          /*if(
             (x != 0 && p != grid[x-1][y])
          || (x != kinect2.depthWidth-1 && p != grid[x+1][y])
          || (y != 0 && p != grid[x][y-1])
          || (y != kinect2.depthHeight-1 && p != grid[x][y+1]))
          {
             display.pixels[pix] = color(255, 0, 255);
             perimeter++;
          }*/
        }
      }
      heights[x] = abs(localbottommost - localtopmost);
    }
    vert = abs(bottommost - topmost);
    for(int i = 0; i < leftmost; i++){
      heights[i] = 0;
    }
    for(int i = rightmost+1; i < kinect2.depthWidth; i++){
      heights[i] = 0;
    }
    boolean onetime = false;
    int wrist = 0;
    for(int x = rightmost-30; x > leftmost+30; x--){
      int leftsum = 0;
      int rightsum = 0;
      for(int xx = 0; xx < 15; xx++){
        leftsum = leftsum + heights[x-xx];
        rightsum = rightsum + heights[x+xx];
      }
      leftsum = leftsum/10;
      rightsum = rightsum/10;
      int diff = leftsum - rightsum;
      if(diff > vert*.04f && onetime == false){
        onetime = true;
        wrist = x;
        for(int y = bottommost; y < topmost; y++){
          int pix = x + y*display.width;
          display.pixels[pix] = color(0, 0, 255);
          
          
        }
      }
    }
    horz = abs(leftmost - wrist);
    
    center[0] = (leftmost+wrist)/2;
    center[1] = (topmost+bottommost)/2;
    
    
    for(int x = leftmost; x < wrist; x++){
      for(int y = bottommost; y < topmost; y++){
        int pix = x + y*display.width;
        int p = grid[x][y];
        
        if(p == 0){
            display.pixels[pix] = color(0,180,0);
            area++;
          }
        
        if(
             (x != 0 && p != grid[x-1][y])
          || (x != kinect2.depthWidth-1 && p != grid[x+1][y])
          || (y != 0 && p != grid[x][y-1])
          || (y != kinect2.depthHeight-1 && p != grid[x][y+1]))
          {
             display.pixels[pix] = color(255, 0, 255);
             perimeter++;
          }
      }
    }
    
    display.updatePixels();
    // Draw the image
    image(display, 0, 0);
    
    rectMode(CENTER);
    noFill();
    stroke(5);
    rect(kinect2.depthWidth/2, kinect2.depthHeight/2, kinect2.depthWidth*.6f, kinect2.depthHeight*.4f);
    
    fill(0,255,0);
    ellipse(kinect2.depthWidth/2, kinect2.depthHeight/2,10,10);
    
  }
   
  public int getWidth(){
    return kinect2.depthWidth;
  }
  public int getHeight(){
    return kinect2.depthHeight;
  }
  
  public int getArea() {
    return area;
  }
  
  public int getPerimeter() {
    return perimeter;
  }
  
  public int getHorz() {
    return horz;
  }
  
  public int getVert() {
    return vert;
  }
  
  public int[] getCenter() {
    return center;
  }
}
class data_saver {
    
  int[] overall = {0,0,0,0};
  int[] first = {0,0,0,0};
  int[] second = {0,0,0,0};
  int[] third = {0,0,0,0};
  
  String[] plays_names = new String[39];
  int[] plays_r = new int[39];
  int[] plays_p = new int[39];
  int[] plays_s = new int[39];
  int[] plays_tot = new int[39];

  
  String[] wins_names = new String[39];
  int[] wins_r = new int[39];
  int[] wins_p = new int[39];
  int[] wins_s = new int[39];
  int[] wins_tot = new int[39];

  String plays_one = "";
  String plays_two = "";
  String plays_three = "";
  
  String wins_one = "";
  String wins_two = "";
  String wins_three = "";
  
  BufferedReader reader;
  String line;
  String[] temp;
  
  data_saver(){
    reader = createReader("save_data.txt");
    try {
      //Header do nothing
      line = reader.readLine();
      //Overall
      line = reader.readLine();
      temp = split(line, ",");
      overall[0] = PApplet.parseInt(temp[0]);
      overall[1] = PApplet.parseInt(temp[1]);
      overall[2] = PApplet.parseInt(temp[2]);
      overall[3] = PApplet.parseInt(temp[3]);
      //first
      line = reader.readLine();
      temp = split(line, ",");
      first[0] = PApplet.parseInt(temp[0]);
      first[1] = PApplet.parseInt(temp[1]);
      first[2] = PApplet.parseInt(temp[2]);
      first[3] = PApplet.parseInt(temp[3]);
      //second
      line = reader.readLine();
      temp = split(line, ",");
      second[0] = PApplet.parseInt(temp[0]);
      second[1] = PApplet.parseInt(temp[1]);
      second[2] = PApplet.parseInt(temp[2]);
      second[3] = PApplet.parseInt(temp[3]);

      //third
      line = reader.readLine();
      temp = split(line, ",");
      third[0] = PApplet.parseInt(temp[0]);
      third[1] = PApplet.parseInt(temp[1]);
      third[2] = PApplet.parseInt(temp[2]);
      third[3] = PApplet.parseInt(temp[3]);

      line = reader.readLine();
      boolean stop = false;
      int i = 0;
      while(stop == false){
        line = reader.readLine();
        println(line);
        if(!line.equals("?end")){
          temp = split(line, ",");
          plays_names[i] = temp[0];
          plays_r[i] = PApplet.parseInt(temp[1]);
          plays_p[i] = PApplet.parseInt(temp[2]);
          plays_s[i] = PApplet.parseInt(temp[3]);
          plays_tot[i] = PApplet.parseInt(temp[4]);

          i++;
        }
        else{
          stop = true;
        }
      }
    
    
      line = reader.readLine();
      stop = false;
      i = 0;
      while(stop == false){
        line = reader.readLine();
        if(!line.equals("?end")){
          temp = split(line, ",");
          wins_names[i] = temp[0];
          wins_r[i] = PApplet.parseInt(temp[1]);
          wins_p[i] = PApplet.parseInt(temp[2]);
          wins_s[i] = PApplet.parseInt(temp[3]);
          wins_tot[i] = PApplet.parseInt(temp[4]);
          i++;
        }
        else{
          stop = true;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public int choose(){
    int plays_one_loc = -1;
    int plays_two_loc = -1;
    int plays_three_loc = -1;

    for(int i = 0; i < plays_names.length; i++){
      if(plays_names[i].equals(plays_one)){
        plays_one_loc = i;
      }
      if(plays_names[i].equals(plays_two)){
        plays_two_loc = i;
      }
      if(plays_names[i].equals(plays_three)){
        plays_three_loc = i;
      }
    }
    double plays_one_r = 0;
    double plays_one_p = 0;
    double plays_one_s = 0;
    
    if(plays_one_loc != -1){
      //Best choice for one move ahead
      plays_one_r = (double)plays_r[plays_one_loc]/plays_tot[plays_one_loc];
      plays_one_p = (double)plays_p[plays_one_loc]/plays_tot[plays_one_loc];
      plays_one_s = (double)plays_s[plays_one_loc]/plays_tot[plays_one_loc];
    }

    double plays_two_r = 0;
    double plays_two_p = 0;
    double plays_two_s = 0;
  
    if(plays_two_loc != -1){
      //Best choice for two moves ahead
      plays_two_r = (double)plays_r[plays_two_loc]/plays_tot[plays_two_loc];
      plays_two_p = (double)plays_p[plays_two_loc]/plays_tot[plays_two_loc];
      plays_two_s = (double)plays_s[plays_two_loc]/plays_tot[plays_two_loc];
    }

    double plays_three_r = 0;
    double plays_three_p = 0;
    double plays_three_s = 0;
  
    if(plays_three_loc != -1){
      //Best choice for three moves ahead
      plays_three_r = (double)plays_r[plays_three_loc]/plays_tot[plays_three_loc];
      plays_three_p = (double)plays_p[plays_three_loc]/plays_tot[plays_three_loc];
      plays_three_s = (double)plays_s[plays_three_loc]/plays_tot[plays_three_loc];
    }
  
    //Best choice based on prev win
    char prev_win_choice = 'x';
    if(wins_one.equals("w") || wins_one.equals("t")){
      if(plays_one.equals("r")){
        prev_win_choice = 'p';
      }
      if(plays_one.equals("p")){
        prev_win_choice = 's';
      }
      if(plays_one.equals("s")){
        prev_win_choice = 'r';
      }
    }
    if(wins_one.equals("l")){
      prev_win_choice = plays_one.charAt(0);
    }

    //Weight choices
    double choice_r = 0;
    double choice_p = 0;
    double choice_s = 0;

    choice_r += plays_one_r + plays_two_r + plays_three_r;
    choice_p += plays_one_p + plays_two_p + plays_three_p;
    choice_s += plays_one_s + plays_two_s + plays_three_s;

    choice_r += random(.15f);
    choice_p += random(.15f);
    choice_s += random(.15f);

    //Prev win weights
    if(prev_win_choice == 'r'){
      choice_r += .3f;
    }
    if(prev_win_choice == 'p'){
      choice_p += .3f;
    }
    if(prev_win_choice == 's'){
      choice_s += .3f;
    }

    //println(choice_r + " " + choice_p + " " + choice_s);
    //Compare weights
    if(choice_r >= choice_p && choice_r >= choice_s){
      return 0;
    }
    if(choice_p >= choice_r && choice_p >= choice_s){
      return 1;
    }
    if(choice_s >= choice_r && choice_s >= choice_p){
      return 2;
    }
    //Default is paper ssshhhh
    println("DEFAULT OOPS");
    return 1;
  }

  public void save(char play, char res){
    save_overall(play);
    save_play(plays_one, plays_two, plays_three, play);
    save_win(wins_one, wins_two, wins_three, play);
    
    plays_one = "";
    plays_one += play;
    wins_one = "";
    wins_one += res;
    
    if(plays_two.length() == 2){
      plays_two = plays_two.substring(1);
    }
    plays_two += play;
    if(wins_two.length() == 2){
      wins_two = wins_two.substring(1);
    }
    wins_two += res;
    
    if(plays_three.length() == 3){
      plays_three = plays_three.substring(1);
    }
    plays_three += play;
    if(wins_three.length() == 3){
      wins_three = wins_three.substring(1);
    }
    wins_three += res;

    //println(wins_one);
    
  }
  
  public void save_overall(char c){
    overall[3]++;
    if(c == 'r'){
      overall[0]++;
    }
    if(c == 'p'){
      overall[1]++;
    }
    if(c == 's'){
      overall[2]++;
    }
  }
  /*void save_first(char c){
    first[3]++;
    if(c == 'r'){
      first[0]++;
    }
    if(c == 'p'){
      first[1]++;
    }
    if(c == 's'){
      first[2]++;
    }
  }
  void save_second(char c){
    second[3]++;
    if(c == 'r'){
      second[0]++;
    }
    if(c == 'p'){
      second[1]++;
    }
    if(c == 's'){
      second[2]++;
    }
  }
  void save_third(char c){
    third[3]++;
    if(c == 'r'){
      third[0]++;
    }
    if(c == 'p'){
      third[1]++;
    }
    if(c == 's'){
      third[2]++;
    }
  }*/
  
  public void save_play(String one, String two, String three, char c){
    int pos_one = -1;
    int pos_two = -1;
    int pos_three = -1;
    for(int i = 0; i < plays_names.length; i++){
      if(plays_names[i].equals(one)){
        pos_one = i;
      }
      if(plays_names[i].equals(two)){
        pos_two = i;
      }
      if(plays_names[i].equals(three)){
        pos_three = i;
      }
    }
    
    if(pos_one != -1 && pos_two != -1 && pos_three != -1){
      if(c == 'r'){
        plays_r[pos_one]++;
        plays_r[pos_two]++;
        plays_r[pos_three]++;
        plays_tot[pos_one]++;
        plays_tot[pos_two]++;
        plays_tot[pos_three]++;
        
      }
      if(c == 'p'){
        plays_p[pos_one]++;
        plays_p[pos_two]++;
        plays_p[pos_three]++;
        plays_tot[pos_one]++;
        plays_tot[pos_two]++;
        plays_tot[pos_three]++;
        
      }
      if(c == 's'){
        plays_s[pos_one]++;
        plays_s[pos_two]++;
        plays_s[pos_three]++;
        plays_tot[pos_one]++;
        plays_tot[pos_two]++;
        plays_tot[pos_three]++;
        
      } 
    }
  }
  public void save_win(String one, String two, String three, char c){
    int pos_one = -1;
    int pos_two = -1;
    int pos_three = -1;
    for(int i = 0; i < wins_names.length; i++){
      if(wins_names[i].equals(one)){
        pos_one = i;
      }
      if(wins_names[i].equals(two)){
        pos_two = i;
      }
      if(wins_names[i].equals(three)){
        pos_three = i;
      }
    }
    if(pos_one != -1 && pos_two != -1 && pos_three != -1){
      
      if(c == 'r'){
        wins_r[pos_one]++;
        wins_r[pos_two]++;
        wins_r[pos_three]++;
        wins_tot[pos_one]++;
        wins_tot[pos_two]++;
        wins_tot[pos_three]++;
        
      }
      if(c == 'p'){
        wins_p[pos_one]++;
        wins_p[pos_two]++;
        wins_p[pos_three]++;
        wins_tot[pos_one]++;
        wins_tot[pos_two]++;
        wins_tot[pos_three]++;
        
      }
      if(c == 's'){
        wins_s[pos_one]++;
        wins_s[pos_two]++;
        wins_s[pos_three]++;
        wins_tot[pos_one]++;
        wins_tot[pos_two]++;
        wins_tot[pos_three]++;
        
      } 
    }
  }
  
  
  public void save_all(Wintrakr w){
    w.saveAll();

    PrintWriter output = createWriter("save_data.txt");
    output.println("?overall-first-second-last");
    output.println(overall[0] + "," + overall[1] + "," + overall[2] + "," + overall[3]);
    output.println(first[0] + "," + first[1] + "," + first[2]+ "," + first[3]);
    output.println(second[0] + "," + second[1] + "," + second[2] + "," + second[3]);
    output.println(third[0] + "," + third[1] + "," + third[2] + "," + third[3]);
    output.println("?plays");
    for(int i = 0; i < 39; i++){
      output.print(plays_names[i]);
      output.print(",");
      output.print(plays_r[i]);
      output.print(",");
      output.print(plays_p[i]);
      output.print(",");
      output.print(plays_s[i]);
      output.print(",");
      output.print(plays_tot[i]);
      output.println();
    }
    output.println("?end");
    output.println("?wins");
    for(int i = 0; i < 39; i++){
      output.print(wins_names[i]);
      output.print(",");
      output.print(wins_r[i]);
      output.print(",");
      output.print(wins_p[i]);
      output.print(",");
      output.print(wins_s[i]);
      output.print(",");
      output.print(wins_tot[i]);
      output.println();
    }
    output.print("?end");
    
    output.flush();
    output.close();
    exit();
  }
}
      
class dtree{

  
  dtree(){
    
  }
  
  public int sigFig(int num, int d){
    println(num);
    float r = PApplet.parseFloat(num);
    r = r/pow(10,d);
    println(r);
    int rounded = round(r);
    rounded = rounded * PApplet.parseInt(pow(10,d));
    println(rounded);  
    return rounded;
  }
  
  public String move(int P, int A, int L, int H){
    println(P + " " + A + " " + L + " " + H);
    int PERIMETER = sigFig(P,2);
    int AREA = sigFig(A,2);
    int LENGTH = sigFig(L,2);
    int HEIGHT = sigFig(H,1);
        
    
    if(PERIMETER == 1700){
      return "SCISSORS";
    }
    if(PERIMETER == 900){
      if(AREA == 25900){
        return "ROCK";
      }
      if(AREA == 15600){
        return "PAPER";
      }
      if(AREA == 16600){
        return "PAPER";
      }
      if(AREA == 9200){
        return "SCISSORS";
      }
      if(AREA == 15900){
        return "PAPER";
      }
      if(AREA == 21100){
        return "PAPER";
      }
      if(AREA == 21400){
        return "ROCK";
      }
      if(AREA == 34000){
        return "PAPER";
      }
      if(AREA == 17200){
        return "PAPER";
      }
      if(AREA == 22600){
        return "ROCK";
      }
      if(AREA == 17900){
        return "PAPER";
      }
      if(AREA == 24200){
        return "ROCK";
      }
      if(AREA == 14300){
        return "PAPER";
      }
      if(AREA == 20300){
        return "ROCK";
      }
      if(AREA == 15000){
        return "PAPER";
      }
      if(AREA == 13500){
        return "PAPER";
      }
      if(AREA == 17000){
        return "PAPER";
      }
      if(AREA == 14200){
        return "PAPER";
      }
      if(AREA == 18200){
        return "ROCK";
      }
      if(AREA == 16700){
        return "PAPER";
      }
      if(AREA == 15100){
        return "PAPER";
      }
      if(AREA == 22000){
        return "ROCK";
      }
      if(AREA == 10400){
        return "PAPER";
      }
      if(AREA == 16900){
        return "PAPER";
      }
      if(AREA == 15200){
        return "PAPER";
      }
      if(AREA == 18500){
        return "ROCK";
      }
      if(AREA == 24100){
        return "PAPER";
      }
      if(AREA == 25000){
        return "ROCK";
      }
      if(AREA == 15300){
        return "PAPER";
      }
      if(AREA == 24600){
        return "ROCK";
      }
      if(AREA == 13300){
        return "PAPER";
      }
      if(AREA == 14800){
        return "PAPER";
      }
      if(AREA == 20700){
        return "ROCK";
      }
      if(AREA == 16000){
        return "PAPER";
      }
      if(AREA == 9400){
        return "SCISSORS";
      }
      if(AREA == 8800){
        return "SCISSORS";
      }
      if(AREA == 21600){
        return "ROCK";
      }
      if(AREA == 23300){
        return "ROCK";
      }
      if(AREA == 19200){
        return "ROCK";
      }
      if(AREA == 18400){
        return "PAPER";
      }
      if(AREA == 15800){
        return "PAPER";
      }
      if(AREA == 16200){
        return "PAPER";
      }
      if(AREA == 9600){
        return "SCISSORS";
      }
      if(AREA == 10700){
        return "PAPER";
      }
      if(AREA == 17500){
        return "PAPER";
      }
      if(AREA == 20900){
        if(HEIGHT == 160){
          return "ROCK";
        }
        if(HEIGHT == 110){
          return "PAPER";
        }
      }
      if(AREA == 18300){
        return "PAPER";
      }
      if(AREA == 14000){
        return "PAPER";
      }
      if(AREA == 14900){
        return "PAPER";
      }
      if(AREA == 16100){
        return "PAPER";
      }
      if(AREA == 19900){
        return "ROCK";
      }
      if(AREA == 19600){
        return "PAPER";
      }
      if(AREA == 32200){
        return "PAPER";
      }
      if(AREA == 34200){
        return "PAPER";
      }
      if(AREA == 15400){
        return "PAPER";
      }
      if(AREA == 21200){
        return "ROCK";
      }
      if(AREA == 18100){
        return "PAPER";
      }
      if(AREA == 16400){
        return "PAPER";
      }
      if(AREA == 20000){
        return "ROCK";
      }
      if(AREA == 16300){
        return "PAPER";
      }
      if(AREA == 15500){
        return "PAPER";
      }
      if(AREA == 18000){
        return "PAPER";
      }
      if(AREA == 13800){
        return "PAPER";
      }
      if(AREA == 14600){
        return "PAPER";
      }
      if(AREA == 21500){
        return "ROCK";
      }
      if(AREA == 20400){
        return "ROCK";
      }
      if(AREA == 19500){
        return "ROCK";
      }
      if(AREA == 13600){
        return "PAPER";
      }
      if(AREA == 14500){
        return "PAPER";
      }
      if(AREA == 24400){
        return "PAPER";
      }
      if(AREA == 19400){
        return "ROCK";
      }
      if(AREA == 11100){
        return "PAPER";
      }
      if(AREA == 14400){
        return "PAPER";
      }
      if(AREA == 12000){
        return "SCISSORS";
      }
      if(AREA == 9800){
        return "SCISSORS";
      }
      if(AREA == 24900){
        return "ROCK";
      }
      if(AREA == 19800){
        return "ROCK";
      }
      if(AREA == 10900){
        return "PAPER";
      }
      if(AREA == 20500){
        return "ROCK";
      }
      if(AREA == 21300){
        return "ROCK";
      }
      if(AREA == 23600){
        return "ROCK";
      }
      if(AREA == 19000){
        return "PAPER";
      }
      if(AREA == 20600){
        return "ROCK";
      }
    }
    if(PERIMETER == 1400){
      return "SCISSORS";
    }
    if(PERIMETER == 1300){
      if(LENGTH == 300){
        return "PAPER";
      }
      if(LENGTH == 200){
        return "SCISSORS";
      }
    }
    if(PERIMETER == 600){
      return "ROCK";
    }
    if(PERIMETER == 1600){
      return "SCISSORS";
    }
    if(PERIMETER == 700){
      if(AREA == 11800){
        return "ROCK";
      }
      if(AREA == 12900){
        return "ROCK";
      }
      if(AREA == 11200){
        return "ROCK";
      }
      if(AREA == 13300){
        return "ROCK";
      }
      if(AREA == 17700){
        return "ROCK";
      }
      if(AREA == 15000){
        return "ROCK";
      }
      if(AREA == 13500){
        return "ROCK";
      }
      if(AREA == 16700){
        return "ROCK";
      }
      if(AREA == 15100){
        return "ROCK";
      }
      if(AREA == 12500){
        return "ROCK";
      }
      if(AREA == 13200){
        return "ROCK";
      }
      if(AREA == 12400){
        return "ROCK";
      }
      if(AREA == 11500){
        return "PAPER";
      }
      if(AREA == 13000){
        return "ROCK";
      }
      if(AREA == 13100){
        return "ROCK";
      }
      if(AREA == 11600){
        return "ROCK";
      }
      if(AREA == 14100){
        return "ROCK";
      }
      if(AREA == 13900){
        return "ROCK";
      }
      if(AREA == 14000){
        return "ROCK";
      }
      if(AREA == 14900){
        return "ROCK";
      }
      if(AREA == 19600){
        return "ROCK";
      }
      if(AREA == 17600){
        return "ROCK";
      }
      if(AREA == 12700){
        return "ROCK";
      }
      if(AREA == 11000){
        return "ROCK";
      }
      if(AREA == 12800){
        return "ROCK";
      }
      if(AREA == 14700){
        return "ROCK";
      }
      if(AREA == 13800){
        return "ROCK";
      }
      if(AREA == 14600){
        return "ROCK";
      }
      if(AREA == 13600){
        return "ROCK";
      }
      if(AREA == 13400){
        return "ROCK";
      }
      if(AREA == 14500){
        return "ROCK";
      }
      if(AREA == 11300){
        return "PAPER";
      }
      if(AREA == 12100){
        return "ROCK";
      }
      if(AREA == 11400){
        return "ROCK";
      }
      if(AREA == 14400){
        return "ROCK";
      }
      if(AREA == 15400){
        return "ROCK";
      }
      if(AREA == 12300){
        return "ROCK";
      }
      if(AREA == 19000){
        return "ROCK";
      }
      if(AREA == 16800){
        return "ROCK";
      }
    }
    if(PERIMETER == 1200){
      if(AREA == 23200){
        return "PAPER";
      }
      if(AREA == 15600){
        return "SCISSORS";
      }
      if(AREA == 21900){
        return "PAPER";
      }
      if(AREA == 34000){
        return "PAPER";
      }
      if(AREA == 30300){
        return "PAPER";
      }
      if(AREA == 30800){
        return "PAPER";
      }
      if(AREA == 17800){
        return "SCISSORS";
      }
      if(AREA == 14300){
        return "SCISSORS";
      }
      if(AREA == 15000){
        return "SCISSORS";
      }
      if(AREA == 13500){
        return "SCISSORS";
      }
      if(AREA == 23300){
        return "PAPER";
      }
      if(AREA == 14200){
        return "SCISSORS";
      }
      if(AREA == 33900){
        return "PAPER";
      }
      if(AREA == 15100){
        return "SCISSORS";
      }
      if(AREA == 30600){
        return "PAPER";
      }
      if(AREA == 30400){
        return "PAPER";
      }
      if(AREA == 26600){
        return "PAPER";
      }
      if(AREA == 29400){
        return "PAPER";
      }
      if(AREA == 13200){
        return "SCISSORS";
      }
      if(AREA == 26700){
        return "PAPER";
      }
      if(AREA == 15300){
        return "SCISSORS";
      }
      if(AREA == 22300){
        return "PAPER";
      }
      if(AREA == 23700){
        return "PAPER";
      }
      if(AREA == 14800){
        return "SCISSORS";
      }
      if(AREA == 16000){
        return "SCISSORS";
      }
      if(AREA == 27300){
        return "PAPER";
      }
      if(AREA == 30700){
        return "PAPER";
      }
      if(AREA == 24300){
        return "PAPER";
      }
      if(AREA == 13100){
        return "SCISSORS";
      }
      if(AREA == 33400){
        return "PAPER";
      }
      if(AREA == 14100){
        return "SCISSORS";
      }
      if(AREA == 13900){
        return "SCISSORS";
      }
      if(AREA == 30000){
        return "PAPER";
      }
      if(AREA == 28800){
        return "PAPER";
      }
      if(AREA == 14000){
        return "SCISSORS";
      }
      if(AREA == 14900){
        return "SCISSORS";
      }
      if(AREA == 24800){
        return "PAPER";
      }
      if(AREA == 32200){
        return "PAPER";
      }
      if(AREA == 27900){
        return "PAPER";
      }
      if(AREA == 26400){
        return "PAPER";
      }
      if(AREA == 15400){
        return "SCISSORS";
      }
      if(AREA == 18100){
        return "SCISSORS";
      }
      if(AREA == 12800){
        return "SCISSORS";
      }
      if(AREA == 26500){
        return "PAPER";
      }
      if(AREA == 15500){
        return "SCISSORS";
      }
      if(AREA == 25300){
        return "PAPER";
      }
      if(AREA == 13800){
        return "SCISSORS";
      }
      if(AREA == 14600){
        return "SCISSORS";
      }
      if(AREA == 29000){
        return "PAPER";
      }
      if(AREA == 33100){
        return "PAPER";
      }
      if(AREA == 13600){
        return "SCISSORS";
      }
      if(AREA == 14500){
        return "SCISSORS";
      }
      if(AREA == 27200){
        return "PAPER";
      }
      if(AREA == 13700){
        return "SCISSORS";
      }
      if(AREA == 14400){
        return "SCISSORS";
      }
      if(AREA == 28000){
        return "PAPER";
      }
      if(AREA == 23000){
        return "PAPER";
      }
      if(AREA == 30200){
        return "PAPER";
      }
      if(AREA == 29600){
        return "PAPER";
      }
      if(AREA == 29800){
        return "PAPER";
      }
      if(AREA == 27400){
        return "PAPER";
      }
      if(AREA == 30100){
        return "PAPER";
      }
    }
    if(PERIMETER == 1500){
      return "SCISSORS";
    }
    if(PERIMETER == 1100){
      if(AREA == 25400){
        return "PAPER";
      }
      if(AREA == 21100){
        return "PAPER";
      }
      if(AREA == 22500){
        return "PAPER";
      }
      if(AREA == 9200){
        return "SCISSORS";
      }
      if(AREA == 20600){
        return "PAPER";
      }
      if(AREA == 22800){
        return "PAPER";
      }
      if(AREA == 21000){
        return "PAPER";
      }
      if(AREA == 31400){
        return "PAPER";
      }
      if(AREA == 34000){
        return "PAPER";
      }
      if(AREA == 11800){
        return "SCISSORS";
      }
      if(AREA == 10300){
        return "SCISSORS";
      }
      if(AREA == 13500){
        return "SCISSORS";
      }
      if(AREA == 13300){
        return "SCISSORS";
      }
      if(AREA == 23300){
        return "PAPER";
      }
      if(AREA == 11300){
        return "SCISSORS";
      }
      if(AREA == 24000){
        return "PAPER";
      }
      if(AREA == 14200){
        return "SCISSORS";
      }
      if(AREA == 21700){
        return "PAPER";
      }
      if(AREA == 26000){
        return "PAPER";
      }
      if(AREA == 30500){
        return "PAPER";
      }
      if(AREA == 30400){
        return "PAPER";
      }
      if(AREA == 12500){
        return "SCISSORS";
      }
      if(AREA == 21500){
        return "PAPER";
      }
      if(AREA == 13200){
        return "SCISSORS";
      }
      if(AREA == 11200){
        return "SCISSORS";
      }
      if(AREA == 12400){
        return "SCISSORS";
      }
      if(AREA == 21400){
        return "PAPER";
      }
      if(AREA == 25600){
        return "PAPER";
      }
      if(AREA == 11500){
        return "SCISSORS";
      }
      if(AREA == 27200){
        return "PAPER";
      }
      if(AREA == 20700){
        return "PAPER";
      }
      if(AREA == 27000){
        return "PAPER";
      }
      if(AREA == 19700){
        return "PAPER";
      }
      if(AREA == 11600){
        return "SCISSORS";
      }
      if(AREA == 24300){
        return "PAPER";
      }
      if(AREA == 25900){
        return "PAPER";
      }
      if(AREA == 24400){
        return "PAPER";
      }
      if(AREA == 11700){
        return "SCISSORS";
      }
      if(AREA == 13000){
        return "SCISSORS";
      }
      if(AREA == 14100){
        return "SCISSORS";
      }
      if(AREA == 24800){
        return "PAPER";
      }
      if(AREA == 13900){
        return "SCISSORS";
      }
      if(AREA == 14000){
        return "SCISSORS";
      }
      if(AREA == 35300){
        return "PAPER";
      }
      if(AREA == 22200){
        return "PAPER";
      }
      if(AREA == 23500){
        return "PAPER";
      }
      if(AREA == 12700){
        return "SCISSORS";
      }
      if(AREA == 29200){
        return "PAPER";
      }
      if(AREA == 10900){
        return "SCISSORS";
      }
      if(AREA == 12800){
        return "SCISSORS";
      }
      if(AREA == 14700){
        return "SCISSORS";
      }
      if(AREA == 12600){
        return "SCISSORS";
      }
      if(AREA == 21200){
        return "PAPER";
      }
      if(AREA == 22600){
        return "PAPER";
      }
      if(AREA == 23700){
        return "PAPER";
      }
      if(AREA == 13600){
        return "SCISSORS";
      }
      if(AREA == 13400){
        return "SCISSORS";
      }
      if(AREA == 20200){
        return "PAPER";
      }
      if(AREA == 12100){
        return "SCISSORS";
      }
      if(AREA == 13100){
        return "SCISSORS";
      }
      if(AREA == 13700){
        return "SCISSORS";
      }
      if(AREA == 11400){
        return "SCISSORS";
      }
      if(AREA == 20300){
        return "PAPER";
      }
      if(AREA == 12000){
        return "SCISSORS";
      }
      if(AREA == 31500){
        return "PAPER";
      }
      if(AREA == 11900){
        return "SCISSORS";
      }
      if(AREA == 21300){
        return "PAPER";
      }
      if(AREA == 12300){
        return "SCISSORS";
      }
      if(AREA == 29800){
        return "PAPER";
      }
      if(AREA == 21600){
        return "PAPER";
      }
      if(AREA == 12200){
        return "SCISSORS";
      }
      if(AREA == 11100){
        return "SCISSORS";
      }
      if(AREA == 30100){
        return "PAPER";
      }
    }
    if(PERIMETER == 800){
      if(AREA == 17100){
        return "ROCK";
      }
      if(AREA == 15600){
        return "ROCK";
      }
      if(AREA == 17600){
        return "ROCK";
      }
      if(AREA == 18700){
        return "ROCK";
      }
      if(AREA == 16600){
        return "ROCK";
      }
      if(AREA == 15900){
        return "ROCK";
      }
      if(AREA == 15700){
        return "ROCK";
      }
      if(AREA == 19200){
        return "ROCK";
      }
      if(AREA == 18600){
        return "ROCK";
      }
      if(AREA == 11800){
        return "PAPER";
      }
      if(AREA == 12900){
        return "PAPER";
      }
      if(AREA == 16800){
        return "ROCK";
      }
      if(AREA == 13400){
        return "PAPER";
      }
      if(AREA == 14300){
        return "PAPER";
      }
      if(AREA == 11500){
        return "PAPER";
      }
      if(AREA == 17700){
        return "ROCK";
      }
      if(AREA == 15000){
        return "ROCK";
      }
      if(AREA == 13500){
        return "PAPER";
      }
      if(AREA == 17500){
        return "ROCK";
      }
      if(AREA == 14200){
        return "PAPER";
      }
      if(AREA == 16700){
        return "ROCK";
      }
      if(AREA == 15100){
        return "PAPER";
      }
      if(AREA == 12500){
        return "PAPER";
      }
      if(AREA == 19500){
        return "ROCK";
      }
      if(AREA == 13200){
        return "PAPER";
      }
      if(AREA == 11200){
        return "PAPER";
      }
      if(AREA == 12400){
        return "PAPER";
      }
      if(AREA == 15300){
        return "ROCK";
      }
      if(AREA == 25600){
        return "ROCK";
      }
      if(AREA == 13300){
        return "PAPER";
      }
      if(AREA == 17400){
        return "ROCK";
      }
      if(AREA == 14800){
        return "ROCK";
      }
      if(AREA == 18500){
        return "ROCK";
      }
      if(AREA == 16000){
        return "ROCK";
      }
      if(AREA == 16900){
        return "ROCK";
      }
      if(AREA == 19700){
        return "ROCK";
      }
      if(AREA == 13000){
        return "PAPER";
      }
      if(AREA == 17900){
        return "ROCK";
      }
      if(AREA == 13100){
        return "PAPER";
      }
      if(AREA == 11600){
        return "PAPER";
      }
      if(AREA == 16200){
        return "ROCK";
      }
      if(AREA == 14100){
        if(HEIGHT == 120){
          return "PAPER";
        }
        if(HEIGHT == 130){
          return "PAPER";
        }
        if(HEIGHT == 140){
          return "ROCK";
        }
      }
      if(AREA == 23100){
        return "ROCK";
      }
      if(AREA == 13900){
        return "PAPER";
      }
      if(AREA == 20900){
        return "ROCK";
      }
      if(AREA == 18300){
        return "ROCK";
      }
      if(AREA == 14000){
        return "ROCK";
      }
      if(AREA == 14900){
        return "ROCK";
      }
      if(AREA == 16100){
        return "ROCK";
      }
      if(AREA == 19600){
        return "ROCK";
      }
      if(AREA == 18200){
        return "ROCK";
      }
      if(AREA == 19000){
        return "ROCK";
      }
      if(AREA == 12700){
        return "PAPER";
      }
      if(AREA == 15400){
        return "ROCK";
      }
      if(AREA == 18100){
        return "ROCK";
      }
      if(AREA == 12800){
        return "PAPER";
      }
      if(AREA == 14700){
        return "ROCK";
      }
      if(AREA == 16300){
        return "ROCK";
      }
      if(AREA == 15500){
        return "ROCK";
      }
      if(AREA == 16500){
        return "ROCK";
      }
      if(AREA == 17800){
        return "ROCK";
      }
      if(AREA == 13800){
        if(HEIGHT == 150){
          return "ROCK";
        }
        if(HEIGHT == 130){
          return "PAPER";
        }
        if(HEIGHT == 110){
          return "PAPER";
        }
      }
      if(AREA == 14600){
        return "ROCK";
      }
      if(AREA == 21500){
        return "ROCK";
      }
      if(AREA == 15800){
        return "ROCK";
      }
      if(AREA == 19100){
        return "ROCK";
      }
      if(AREA == 13600){
        return "PAPER";
      }
      if(AREA == 18800){
        return "ROCK";
      }
      if(AREA == 11300){
        if(HEIGHT == 120){
          return "PAPER";
        }
        if(HEIGHT == 110){
          return "PAPER";
        }
        if(HEIGHT == 170){
          return "SCISSORS";
        }
      }
      if(AREA == 12100){
        return "PAPER";
      }
      if(AREA == 10800){
        return "PAPER";
      }
      if(AREA == 13700){
        return "PAPER";
      }
      if(AREA == 14400){
        if(LENGTH == 200){
          return "PAPER";
        }
        if(LENGTH == 100){
          return "ROCK";
        }
      }
      if(AREA == 12000){
        return "PAPER";
      }
      if(AREA == 11700){
        return "PAPER";
      }
      if(AREA == 19300){
        return "ROCK";
      }
      if(AREA == 17300){
        return "ROCK";
      }
      if(AREA == 19800){
        return "ROCK";
      }
      if(AREA == 10900){
        return "PAPER";
      }
      if(AREA == 17200){
        return "ROCK";
      }
      if(AREA == 18900){
        return "ROCK";
      }
      if(AREA == 19400){
        return "ROCK";
      }
      if(AREA == 12200){
        return "PAPER";
      }
      if(AREA == 11100){
        return "PAPER";
      }
    }
    if(PERIMETER == 500){
      return "ROCK";
    }
    if(PERIMETER == 1000){
      if(AREA == 17100){
        return "PAPER";
      }
      if(AREA == 16200){
        return "PAPER";
      }
      if(AREA == 18700){
        return "PAPER";
      }
      if(AREA == 10000){
        return "SCISSORS";
      }
      if(AREA == 16400){
        return "PAPER";
      }
      if(AREA == 19200){
        return "PAPER";
      }
      if(AREA == 32600){
        return "PAPER";
      }
      if(AREA == 11800){
        return "SCISSORS";
      }
      if(AREA == 9100){
        return "SCISSORS";
      }
      if(AREA == 10300){
        return "SCISSORS";
      }
      if(AREA == 11200){
        return "SCISSORS";
      }
      if(AREA == 20400){
        return "PAPER";
      }
      if(AREA == 17700){
        return "PAPER";
      }
      if(AREA == 11300){
        return "SCISSORS";
      }
      if(AREA == 17500){
        return "PAPER";
      }
      if(AREA == 20500){
        return "PAPER";
      }
      if(AREA == 9300){
        return "SCISSORS";
      }
      if(AREA == 10100){
        return "SCISSORS";
      }
      if(AREA == 34500){
        return "PAPER";
      }
      if(AREA == 23600){
        return "ROCK";
      }
      if(AREA == 16900){
        return "PAPER";
      }
      if(AREA == 16800){
        return "PAPER";
      }
      if(AREA == 32300){
        return "PAPER";
      }
      if(AREA == 23000){
        return "ROCK";
      }
      if(AREA == 11500){
        return "SCISSORS";
      }
      if(AREA == 16700){
        return "PAPER";
      }
      if(AREA == 18500){
        return "PAPER";
      }
      if(AREA == 10600){
        return "SCISSORS";
      }
      if(AREA == 9400){
        return "SCISSORS";
      }
      if(AREA == 23200){
        return "ROCK";
      }
      if(AREA == 18400){
        return "PAPER";
      }
      if(AREA == 19400){
        return "PAPER";
      }
      if(AREA == 10400){
        return "SCISSORS";
      }
      if(AREA == 9600){
        return "SCISSORS";
      }
      if(AREA == 20000){
        return "PAPER";
      }
      if(AREA == 17200){
        return "PAPER";
      }
      if(AREA == 13900){
        return "SCISSORS";
      }
      if(AREA == 22400){
        return "PAPER";
      }
      if(AREA == 18300){
        return "PAPER";
      }
      if(AREA == 20700){
        return "PAPER";
      }
      if(AREA == 10700){
        return "SCISSORS";
      }
      if(AREA == 18200){
        return "PAPER";
      }
      if(AREA == 17600){
        return "PAPER";
      }
      if(AREA == 21300){
        return "PAPER";
      }
      if(AREA == 10200){
        return "SCISSORS";
      }
      if(AREA == 9000){
        return "SCISSORS";
      }
      if(AREA == 9700){
        return "SCISSORS";
      }
      if(AREA == 10500){
        return "SCISSORS";
      }
      if(AREA == 16500){
        return "PAPER";
      }
      if(AREA == 34600){
        return "PAPER";
      }
      if(AREA == 17400){
        return "PAPER";
      }
      if(AREA == 11000){
        return "SCISSORS";
      }
      if(AREA == 18800){
        return "PAPER";
      }
      if(AREA == 14500){
        return "SCISSORS";
      }
      if(AREA == 9900){
        return "SCISSORS";
      }
      if(AREA == 17800){
        return "PAPER";
      }
      if(AREA == 11100){
        return "SCISSORS";
      }
      if(AREA == 20300){
        return "PAPER";
      }
      if(AREA == 16600){
        return "PAPER";
      }
      if(AREA == 9800){
        return "SCISSORS";
      }
      if(AREA == 19300){
        return "PAPER";
      }
      if(AREA == 17300){
        return "PAPER";
      }
      if(AREA == 19800){
        return "PAPER";
      }
      if(AREA == 10900){
        return "SCISSORS";
      }
      if(AREA == 19000){
        return "PAPER";
      }
      if(AREA == 10800){
        return "SCISSORS";
      }
      if(AREA == 24100){
        return "ROCK";
      }
      if(AREA == 22500){
        return "ROCK";
      }
      if(AREA == 20800){
        return "PAPER";
      }
    }
    
    return move(P+50, A+100, L+10, H+10);
  }
}
class Wintrakr{
	int wins;//player wins comp loses
	int losses;
	int errors;
	int games;

	BufferedReader reader;
 	String line;
 	String[] temp;

	Wintrakr(){
		reader = createReader("wins.txt");
	    try {
	      //Header do nothing
	      line = reader.readLine();
	      temp = split(line, ",");
	      wins = PApplet.parseInt(temp[0]);
	      losses = PApplet.parseInt(temp[1]);
	      errors = PApplet.parseInt(temp[2]);
	      games = PApplet.parseInt(temp[3]);
	  	}
	  	catch (IOException e) {
      		e.printStackTrace();
    	}	
  	}

	public void update(char c){
		if(c == 'w'){
			wins++;
			games++;
		}
		if(c == 'l'){
			losses++;
			games++;
		}
		if(c == 't'){
			games++;
		}
		if(c == 'e'){
			errors++;
		}
	}

	public void saveAll(){
		PrintWriter output = createWriter("wins.txt");
		output.println(wins + "," + losses + "," + errors + "," + games);
		output.flush();
    	output.close();
	}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "HandTrack" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
