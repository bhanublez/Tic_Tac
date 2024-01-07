package files.tic_tac;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import files.tic_tac.R.*;


public class Board extends View {
    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int diagonalColor;
    private boolean winningLine;
    private final Paint paint;
    private final GameLogic game;
    private int cellSize;

    public Board(Context context) {
        this(context, (AttributeSet)null);
    }

    public Board(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Board(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.winningLine = false;
        this.paint = new Paint();
        this.cellSize = this.getWidth() / 3;
        this.game = new GameLogic();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, styleable.TicTacBoard, 0, 0);

        try {
            this.boardColor = a.getInteger(styleable.TicTacBoard_boardColor, 0);
            this.XColor = a.getInteger(styleable.TicTacBoard_XColor, 0);
            this.OColor = a.getInteger(styleable.TicTacBoard_OColor, 0);
            this.diagonalColor = a.getInteger(styleable.TicTacBoard_WinningLineColor, 0);
        } finally {
            a.recycle();
        }

    }

    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        int dimension = Math.min(this.getMeasuredWidth(), this.getMeasuredHeight());
        this.cellSize = dimension / 3;
        this.setMeasuredDimension(dimension, dimension);
    }

    protected void onDraw(Canvas canvas) {
        this.paint.setStyle(Style.STROKE);
        this.paint.setAntiAlias(true);
        this.drawGameBoard(canvas);
        this.drawMarkers(canvas);
        if (this.winningLine) {
            this.paint.setColor(this.diagonalColor);
            this.drawWinningLine(canvas);
        }

    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        if (action == 0) {
            int row = (int)Math.ceil((double)(y / (float)this.cellSize));
            int col = (int)Math.ceil((double)(x / (float)this.cellSize));
            if (!this.winningLine && this.game.updateGameBoard(row, col)) {
                this.invalidate();
                if (this.game.winnerCheck()) {
                    this.winningLine = true;
                    this.invalidate();
                }

                if (this.game.getPlayer() % 2 == 0) {
                    this.game.setPlayer(this.game.getPlayer() - 1);
                } else {
                    this.game.setPlayer(this.game.getPlayer() + 1);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    private void drawGameBoard(Canvas canvas) {
        this.paint.setColor(this.boardColor);
        this.paint.setStrokeWidth(16.0F);

        int r;
        for(r = 1; r < 3; ++r) {
            canvas.drawLine((float)(this.cellSize * r), 0.0F, (float)(this.cellSize * r), (float)canvas.getWidth(), this.paint);
        }

        for(r = 1; r < 3; ++r) {
            canvas.drawLine(0.0F, (float)(this.cellSize * r), (float)canvas.getWidth(), (float)(this.cellSize * r), this.paint);
        }

    }

    private void drawMarkers(Canvas canvas) {
        for(int r = 0; r < 3; ++r) {
            for(int c = 0; c < 3; ++c) {
                if (this.game.getGameBoard()[r][c] != 0) {
                    if (this.game.getGameBoard()[r][c] == 1) {
                        this.drawX(canvas, r, c);
                    } else {
                        this.drawO(canvas, r, c);
                    }
                }
            }
        }

    }

    private void drawX(Canvas canvas, int row, int col) {
        this.paint.setColor(this.XColor);
        canvas.drawLine((float)((double)((col + 1) * this.cellSize) - (double)this.cellSize * 0.2), (float)((double)(row * this.cellSize) + (double)this.cellSize * 0.2), (float)((double)(col * this.cellSize) + (double)this.cellSize * 0.2), (float)((double)((row + 1) * this.cellSize) - (double)this.cellSize * 0.2), this.paint);
        canvas.drawLine((float)((double)(col * this.cellSize) + (double)this.cellSize * 0.2), (float)((double)(row * this.cellSize) + (double)this.cellSize * 0.2), (float)((double)((col + 1) * this.cellSize) - (double)this.cellSize * 0.2), (float)((double)((row + 1) * this.cellSize) - (double)this.cellSize * 0.2), this.paint);
    }

    private void drawO(Canvas canvas, int row, int col) {
        this.paint.setColor(this.OColor);
        canvas.drawOval((float)((double)(col * this.cellSize) + (double)this.cellSize * 0.2), (float)((double)(row * this.cellSize) + (double)this.cellSize * 0.2), (float)((double)(col * this.cellSize + this.cellSize) - (double)this.cellSize * 0.2), (float)((double)(row * this.cellSize + this.cellSize) - (double)this.cellSize * 0.2), this.paint);
    }

    private void drawWinningLine(Canvas canvas) {
        int row = this.game.getWinType()[0];
        int col = this.game.getWinType()[1];
        switch (this.game.getWinType()[2]) {
            case 1:
                this.drawHorizontalLine(canvas, row, col);
                break;
            case 2:
                this.drawVerticalLine(canvas, row, col);
                break;
            case 3:
                this.drawDiagonalLineNeg(canvas);
                break;
            case 4:
                this.drawDiagonalLinePos(canvas);
        }

    }

    private void drawHorizontalLine(Canvas canvas, int row, int col) {
        canvas.drawLine((float)col, (float)(row * this.cellSize) + (float)this.cellSize / 2.0F, (float)(this.cellSize * 3), (float)(row * this.cellSize) + (float)this.cellSize / 2.0F, this.paint);
    }

    private void drawVerticalLine(Canvas canvas, int row, int col) {
        canvas.drawLine((float)(col * this.cellSize) + (float)this.cellSize / 2.0F, (float)row, (float)(col * this.cellSize) + (float)this.cellSize / 2.0F, (float)(this.cellSize * 3), this.paint);
    }

    private void drawDiagonalLinePos(Canvas canvas) {
        canvas.drawLine(0.0F, (float)(this.cellSize * 3), (float)(this.cellSize * 3), 0.0F, this.paint);
    }

    private void drawDiagonalLineNeg(Canvas canvas) {
        canvas.drawLine(0.0F, 0.0F, (float)(this.cellSize * 3), (float)(this.cellSize * 3), this.paint);
    }

    public void resetGame() {
        this.game.resetGame();
        this.winningLine = false;
    }

    public void setUpGame(TextView playAgainButton, TextView homeButton, TextView playerDisplay, String[] playerNames) {
        this.game.setPlayAgainBTN(playAgainButton);
        this.game.setHomeBTN(homeButton);
        this.game.setPlayerTurn(playerDisplay);
        this.game.setPlayerNames(playerNames);
    }
}

