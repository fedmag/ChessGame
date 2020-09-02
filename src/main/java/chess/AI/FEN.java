package chess.AI;

import chess.pieces.Piece;
import chess.src.Board;

public class FEN {

    public FEN() {}

    public static String  translateToFEN(Board board) {
        String fenString = "";
        int counter = 0;
        for (int i = 7; i >= 0 ; i--) {
            for (int j = 0; j <= 7; j++) {
                Piece piece = board.pieceAtDest(i,j);
                if (piece!=null) {
                    if (counter!=0) {
                        fenString += counter;
                        counter = 0;
                    }
                    if (piece.getWhite()) fenString += (piece.getPieceName().substring(0,1));
                    else fenString += (piece.getPieceName().substring(0,1).toUpperCase());
                }
                else {
                    counter ++;
                    if (counter==8) {
                        fenString += counter;
                        counter = 0;
                    }
                }
            } fenString += "/";
        }
        System.out.println(fenString);
        return fenString;
    }
}
