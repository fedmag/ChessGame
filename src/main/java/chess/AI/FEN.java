package chess.AI;

import chess.pieces.King;
import chess.pieces.Piece;
import chess.pieces.Rook;
import chess.src.Board;
import chess.src.GameFlow;
import chess.src.Player;

public class FEN {

    public FEN() {}

    public static String  translateToFEN(Board board, Player p1, Player p2) {
        String finalFENString;
        finalFENString = gridStatus(board, p1, p2);
        finalFENString = castlingStatus(finalFENString, board, p1, p2);
//        finalFENString.append(enPassantStatus(finalFENString, board, p1, p2));
        System.out.println(finalFENString.toString());
        return finalFENString.toString();
    }


    private static String gridStatus(Board board, Player p1, Player p2) {
        StringBuilder fenString = new StringBuilder();
        int counter = 0;
        for (int i = 7; i >= 0 ; i--) { // moving bottom to top
            for (int j = 0; j <= 7; j++) { // moving from left to right
                Piece piece = board.pieceAtDest(i,j);
                if (piece!=null) {
                    if (counter!=0) {
                        fenString.append(counter);
                        counter=0;
                    }
                    if (piece.getPieceName().equals("knight")) { // for some reason the knight is N in the FEN notation
                        if (piece.getWhite()) fenString.append(("n"));
                        else fenString.append("N");
                    } else {
                        if (piece.getWhite()) fenString.append((piece.getPieceName().substring(0, 1)));
                        else fenString.append(piece.getPieceName().substring(0, 1).toUpperCase());
                        counter = 0;
                    }
                } else {
                    counter ++;
                    if (counter==8) {
                        fenString.append(counter);
                        counter = 0;
                    }
                }
            }
            if (counter!=0) {
                fenString.append(counter);
                counter = 0;
            }
            fenString.append("/");
        }
        fenString.append(" "+ GameFlow.whoseTurnColor(p1, p2));
        return fenString.toString();
    }

    private static String castlingStatus(String fenString, Board board, Player p1, Player p2) {
        StringBuilder workingFENString = new StringBuilder(fenString);
        StringBuilder initialCastlingString = new StringBuilder();
        Piece whiteLeftRook = board.pieceAtDest(7,0);
        Piece whiteRightRook = board.pieceAtDest(7,7);
        Piece whiteKing = board.pieceAtDest(7,4);

        if (whiteKing instanceof King && whiteKing.getWhite() && !((King) whiteKing).isCastlingDone()) { // castling not performed on the king
            if (!((Rook) whiteRightRook).isCastlingDone()) initialCastlingString.append("K");
            if (!((Rook) whiteLeftRook).isCastlingDone()) initialCastlingString.append("Q");
        }
        Piece blackLeftRook = board.pieceAtDest(0,0);
        Piece blackRightRook = board.pieceAtDest(0,7);
        Piece blackKing = board.pieceAtDest(0,4);
        if (blackKing instanceof King && !blackKing.getWhite() && !((King) blackKing).isCastlingDone()) { // castling not performed on the king
            if (!((Rook) blackRightRook).isCastlingDone()) initialCastlingString.append("k");
            if (!((Rook) blackLeftRook).isCastlingDone()) initialCastlingString.append("q");
        }
        if (initialCastlingString.toString().equals("")) initialCastlingString.append("-");
        return workingFENString.append(" "+ initialCastlingString).toString();
//        return workingFENString.toString();
    }
}
