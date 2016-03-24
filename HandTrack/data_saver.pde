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
      overall[0] = int(temp[0]);
      overall[1] = int(temp[1]);
      overall[2] = int(temp[2]);
      overall[3] = int(temp[3]);
      //first
      line = reader.readLine();
      temp = split(line, ",");
      first[0] = int(temp[0]);
      first[1] = int(temp[1]);
      first[2] = int(temp[2]);
      first[3] = int(temp[3]);
      //second
      line = reader.readLine();
      temp = split(line, ",");
      second[0] = int(temp[0]);
      second[1] = int(temp[1]);
      second[2] = int(temp[2]);
      second[3] = int(temp[3]);

      //third
      line = reader.readLine();
      temp = split(line, ",");
      third[0] = int(temp[0]);
      third[1] = int(temp[1]);
      third[2] = int(temp[2]);
      third[3] = int(temp[3]);

      line = reader.readLine();
      boolean stop = false;
      int i = 0;
      while(stop == false){
        line = reader.readLine();
        println(line);
        if(!line.equals("?end")){
          temp = split(line, ",");
          plays_names[i] = temp[0];
          plays_r[i] = int(temp[1]);
          plays_p[i] = int(temp[2]);
          plays_s[i] = int(temp[3]);
          plays_tot[i] = int(temp[4]);

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
          wins_r[i] = int(temp[1]);
          wins_p[i] = int(temp[2]);
          wins_s[i] = int(temp[3]);
          wins_tot[i] = int(temp[4]);
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
  
  int choose(){
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

    choice_r += random(.15);
    choice_p += random(.15);
    choice_s += random(.15);

    //Prev win weights
    if(prev_win_choice == 'r'){
      choice_r += .3;
    }
    if(prev_win_choice == 'p'){
      choice_p += .3;
    }
    if(prev_win_choice == 's'){
      choice_s += .3;
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

  void save(char play, char res){
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
  
  void save_overall(char c){
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
  
  void save_play(String one, String two, String three, char c){
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
  void save_win(String one, String two, String three, char c){
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
  
  
  void save_all(Wintrakr w){
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
      
