import java.util.*;

public class Move
{
  public int to;
  public int from;
  public boolean hasCaptured;
  public int capturedPiece;
  public int movedPiece; 
   
  public Move(int from,int to, boolean hasCaptured, int capturedPiece, int movedPiece)
  {
    this.from = from;
    this.to = to;
    this.hasCaptured = hasCaptured;
    this.capturedPiece = capturedPiece;
    this.movedPiece = movedPiece;
  }
  public Move(int from,int to,int movedPiece)
  {
    this.from = from;
    this.to = to;
    this.hasCaptured = false;
    this.capturedPiece = -1;
    this.movedPiece = movedPiece;
  }
  @Override
  public String toString() {
    HashMap<Integer, Character> piecetoChar = new HashMap<>();
    piecetoChar.put(0,'K');
    piecetoChar.put(1,'N');
    piecetoChar.put(2,'Q');
    piecetoChar.put(3,'P');
    piecetoChar.put(4,'B');
    piecetoChar.put(5,'R');
    int xto = (this.to%12) - 2;
    int yto = 8-((this.to/12) - 2);
    int xfrom = (this.from%12) - 2;
    int yfrom = 8-((this.from/12) - 2);    
    if(this.hasCaptured)
    {
      return(piecetoChar.get(movedPiece/2)+""+(char)(xto+(int)'a')+yto);
    }
    else
    {
      return(piecetoChar.get(movedPiece/2)+""+(char)(xto+(int)'a')+yto);
    }
  }
}