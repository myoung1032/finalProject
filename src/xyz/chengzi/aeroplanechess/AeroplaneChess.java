package xyz.chengzi.aeroplanechess;

import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.view.ChessBoardComponent;
import xyz.chengzi.aeroplanechess.view.GameFrame;



public class AeroplaneChess {
    public static void main(String[] args) {

            ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 13, 6);//endDimension表示终点前的格数
            ChessBoard chessBoard = new ChessBoard(13, 6);
            GameController controller = new GameController(chessBoardComponent, chessBoard);

            GameFrame mainFrame = new GameFrame(controller);
            mainFrame.add(chessBoardComponent);
            mainFrame.setVisible(true);
           // controller.initializeGame(  );

    }

}
