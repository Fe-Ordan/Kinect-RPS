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
  
  void display() {
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
        
        if(x > kinect2.depthWidth*.2 && x < kinect2.depthWidth * .8 && y > kinect2.depthHeight*.3 && y < kinect2.depthHeight * .7){
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
      if(diff > vert*.04 && onetime == false){
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
    rect(kinect2.depthWidth/2, kinect2.depthHeight/2, kinect2.depthWidth*.6, kinect2.depthHeight*.4);
    
    fill(0,255,0);
    ellipse(kinect2.depthWidth/2, kinect2.depthHeight/2,10,10);
    
  }
   
  int getWidth(){
    return kinect2.depthWidth;
  }
  int getHeight(){
    return kinect2.depthHeight;
  }
  
  int getArea() {
    return area;
  }
  
  int getPerimeter() {
    return perimeter;
  }
  
  int getHorz() {
    return horz;
  }
  
  int getVert() {
    return vert;
  }
  
  int[] getCenter() {
    return center;
  }
}
