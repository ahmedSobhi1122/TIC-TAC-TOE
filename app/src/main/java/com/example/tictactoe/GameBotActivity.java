package com.example.tictactoe;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.annotation.NonNull;

public class GameBotActivity extends BaseActivity {
    ImageButton[][] blocks;
    Button reset, score;
    Player player1;
    Bot bot;
    Game game;
    ImageButton settingsBtn, modeBtn;
    MediaPlayer mp,mpWin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadSound();
        loadMode();
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        blocks = new ImageButton[3][3];
        blocks[0][0] = findViewById(R.id.block1);
        blocks[0][1] = findViewById(R.id.block2);
        blocks[0][2] = findViewById(R.id.block3);
        blocks[1][0] = findViewById(R.id.block4);
        blocks[1][1] = findViewById(R.id.block5);
        blocks[1][2] = findViewById(R.id.block6);
        blocks[2][0] = findViewById(R.id.block7);
        blocks[2][1] = findViewById(R.id.block8);
        blocks[2][2] = findViewById(R.id.block9);
        settingsBtn = findViewById(R.id.settings);
        modeBtn = findViewById(R.id.mode);

        reset = findViewById(R.id.game_reset);
        score = findViewById(R.id.game_score);

        player1 = new Player(loadDataString(KEY_PLAYER1_NAME));
        bot = new Bot(loadDataString(KEY_BOT_LEVEL));
        game = new Game();

        saveDataString(KEY_PLAYER2_NAME, bot.getName());
        settingsBtn.setOnClickListener(v -> goTo(getApplication(),SettingActivity.class));
        modeBtn.setOnClickListener(v -> toggleNightMode());
        reset.setOnClickListener(view -> resetBoard());
        score.setOnClickListener(view -> goTo(GameBotActivity.this, ScoreActivity.class));

        for (int i = 0; i < 9; i++){
            int row = i / 3, col = i % 3;
            blocks[row][col].setOnClickListener(v -> {
                if (blocks[row][col].getDrawable() == null && game.getWinner().isEmpty()) {
                    mp = MediaPlayer.create(this, CLICK_SOUND);
                    mpWin = MediaPlayer.create(this, WIN_SOUND);
                    mp.start();
                    game.makeMove(row, col);
                    setImage(blocks[row][col]);
                    setWinPos();
                    game.toggle();
                    if(!game.isBoardFull()) {
                        int []arr = bot.select(game.board);
                        int r = arr[0], c = arr[1];
                        if (blocks[r][c].getDrawable() == null && game.getWinner().isEmpty()) {
                            game.makeMove(r, c);
                            setImage(blocks[r][c]);
                            setWinPos();
                            game.toggle();
                        }
                    }
                }
            });
        }
    }

    public void setWinPos() {
        if(!game.winner.isEmpty()) mpWin.start();
        boolean[][] winPos = game.getWinPos();
        for (int i = 0; i < 9; i++)
            if (winPos[i / 3][i % 3])
                setColor(blocks[i / 3][i % 3]);
        if (game.getWinner().equals("x"))
            saveDataInt(KEY_PLAYER1_SCORE, loadDataInt(KEY_PLAYER1_SCORE) + 1);
        else if (game.getWinner().equals("o"))
            saveDataInt(KEY_PLAYER2_SCORE, loadDataInt(KEY_PLAYER2_SCORE) + 1);
    }
    public void setColor(@NonNull ImageButton btn) {
        btn.setImageResource(game.getWinner().equals("x") ? R.drawable.x_win : R.drawable.o_win);
    }
    public void resetBoard() {
        for (int i = 0; i < 9; i++) blocks[i / 3][i % 3].setImageResource(0);
        game.reset();
    }
    void setImage(@NonNull ImageButton btn) {
        if(game.getCurrentPlayer() == 'x') btn.setImageResource(X);
        else btn.setImageResource(O);
    }
}