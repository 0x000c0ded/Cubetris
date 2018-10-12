package sample;

public class Score {
    private double b = 0.35;
    private double a = -b/7000;
    int nbLineC ;
    int actuelLevel ;
    int score ;
    int speed ;
    int matrixScoreBeg[] = {40,100,300,1200};

    public Score () {
        this.nbLineC = 0 ;
        this.score = 0 ;
        this.actuelLevel = 0 ;
        this.speed = 500 ;
    }
    /*getter et setter*/
    public int getNbLinceC() {
        return this.nbLineC;
    }

    public int getSpeed() {
        return this.speed ;
    }

    public int getActuelLevel() {
        return this.actuelLevel ;
    }

    public int getScore() {
        return this.score;
    }

    public void setNbLineC(int nbLineC) {
        this.nbLineC = nbLineC ;
    }

    public void setScore(int score) {
        this.score = score ;
    }

    public void setActuelLevel(int actuelLevel) {
        this.actuelLevel = actuelLevel ;
    }

    public void setSpeed ( int speed ) {
        this.speed = speed ;
    }

    public void newScore (Arena arena) {
        this.score += matrixScoreBeg[Utils.len(arena.getCompletedLines())-1]*(this.actuelLevel + 1);
        InGameWindow.expectedPiecePosition.opacity = Math.max(0, a*this.score + b);

    }

    public void incLevel (Arena arena) {
        if ( this.nbLineC % 15 < Utils.len(arena.getCompletedLines()) && this.actuelLevel <9) {
            this.actuelLevel ++;
            updateSpeed();
        }
    }


    public void updateSpeed () {
        this.speed =  -50*this.actuelLevel +500;

    }

    public void manageWidgets (Arena arena) {//on fait apl a la methode au moment qu'on a une destruction ( arene.checkLine != 0 ) //
        this.newScore(arena);
        this.nbLineC += Utils.len(arena.getCompletedLines()) ;
        this.incLevel(arena);

    }
}

