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
	      wins = int(temp[0]);
	      losses = int(temp[1]);
	      errors = int(temp[2]);
	      games = int(temp[3]);
	  	}
	  	catch (IOException e) {
      		e.printStackTrace();
    	}	
  	}

	void update(char c){
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

	void saveAll(){
		PrintWriter output = createWriter("wins.txt");
		output.println(wins + "," + losses + "," + errors + "," + games);
		output.flush();
    	output.close();
	}
}