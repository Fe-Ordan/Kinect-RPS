class dtree{

  
  dtree(){
    
  }
  
  int sigFig(int num, int d){
    println(num);
    float r = float(num);
    r = r/pow(10,d);
    println(r);
    int rounded = round(r);
    rounded = rounded * int(pow(10,d));
    println(rounded);  
    return rounded;
  }
  
  String move(int P, int A, int L, int H){
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
