package sample;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Joker extends Parent {
	public int numberOfUse = 3 ;
	public boolean jokerUsed ;
	Image jok = new Image("images/award.png");
	ImageView jokers [] = {new ImageView(jok) , new ImageView(jok) ,new ImageView(jok)};
	
	
	
	
	
	public Joker (int x , int y) {
		this.numberOfUse = 3;	
		final int c = 30 ;
		for ( int i = 0 ; i<=2 ; i++) {
			
			jokers[i].setFitHeight(c);
			jokers[i].setFitWidth(c);
			jokers[i].setOpacity(0.8);
			jokers[i].setTranslateX(x+c*i+2*i);
			jokers[i].setTranslateY(y);
			
		}	
		jokerUsed = false ;
				
		this.getChildren().addAll(jokers);

		}
	
	public void useJoker(int[] linesToDestroy, Score widgets , Arena arene) {
		if (widgets.score >= 1000 &&  numberOfUse <=3 && numberOfUse >0) {

			arene.editCompletedLines(linesToDestroy);
			arene.succeeded = true;
			widgets.score = widgets.score -1000;
			numberOfUse -- ;
		
		}
	}
		public void jokerActivated (Score widgets , Arena arene) {
			int linesToDestroy[] = notEmptyLines(arene);
			if (Utils.len(linesToDestroy)!=0) {
				if (widgets.score >= 1000 &&  numberOfUse <=3 && numberOfUse >0) {
					useJoker(linesToDestroy ,widgets, arene);
					jokers[2-numberOfUse].setOpacity(0.1);
					jokerUsed = true ;
				}
			}
			
		}


		
		public int[] notEmptyLines(Arena arene) {
			int linesToDestruct[] = {-1, -1, -1, -1};
			int id = 0;
			int l = 22;
			boolean addedLine;
			while (l < 26){
				int i = 0;
				addedLine = false;
				while (i < 10 && !addedLine) {
					if ( arene.getMatrixValueAt(l, i) != 0) {
						linesToDestruct[id] = l;
						addedLine = true;
						id++;
					}
					i++;
				}
				l++;
			}
			return linesToDestruct;
		}

	}
	
	
	
	


