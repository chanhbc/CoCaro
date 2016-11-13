package com.chanhbc.caro.model;

import com.chanhbc.caro.gui.ChessSquare;

public class Caro {
    private int[][] arrCaro;
    private int own; // own = 1 là X, 2 là O
    private String message;
    private int playMode;
    private double[] SCORE_ATTACK = new double[]{0, 64, 4096, 262144, 16777216, 1073741824}; //Điểm tấn công
    private double[] SCORE_DEFEND = {0, 8, 512, 32768, 2097152, 134217728}; // Điểm phòng thủ

    public Caro(int[][] arrCaro, int playMode) {
        this.arrCaro = arrCaro;
        this.playMode = playMode;
    }

    public int getOwn() {
        return own;
    }

    public void setOwn(int own) {
        this.own = own;
    }

    public String getMessage() {
        return message;
    }

    public boolean checkGame() {
        int dem = 0;
        for (int i = 0; i < arrCaro.length; i++) {
            for (int j = 0; j < arrCaro[0].length; j++) {
                if (checkVertical(i, j) == true || checkHorizontal(i, j) == true
                        || checkDiagonalLeft(i, j) == true || checkDiagonalRight(i, j) == true) {
                    if (playMode == 2) {
                        if (own == 1)
                            message = "Người chơi 1 thắng!";
                        else if (own == 2)
                            message = "Người chơi 2 thắng!";
                    } else if (playMode == 1) {
                        if (own == 1)
                            message = "Người chơi thắng!";
                        else if (own == 2)
                            message = "Máy thắng!";
                    }
                    return true;
                }
                if (arrCaro[i][j] == 0) {
                    dem++;
                }
            }
        }
        if (dem == 0) {
            message = "Hòa cờ!";
            return true;// không còn vị trí trống trên bàn cờ(hòa cờ)
        }
        return false;
    }

    public boolean checkVertical(int x, int y) {
        if (x > arrCaro.length - 5)
            return false;
        for (int i = 1; i <= 5; i++) {
            if (arrCaro[x + i][y] != own) {
                return false;
            }
        }
        return true;
    }

    public boolean checkHorizontal(int x, int y) {
        if (y > arrCaro[0].length - 5)
            return false;
        for (int i = 1; i <= 5; i++) {
            if (arrCaro[x][y + i] != own) {
                return false;
            }
        }
        return true;
    }

    public boolean checkDiagonalLeft(int x, int y) {
        if (x > arrCaro.length - 5 || y > arrCaro[0].length - 5)
            return false;
        for (int i = 1; i <= 5; i++) {
            if (arrCaro[x + i][y + i] != own) {
                return false;
            }
        }
        return true;
    }

    public boolean checkDiagonalRight(int x, int y) {
        if (x < 4 || y > arrCaro[0].length - 5)
            return false;
        for (int i = 1; i <= 5; i++) {
            if (arrCaro[x - i][y + i] != own) {
                return false;
            }
        }
        return true;
    }

    public Point startComputer() {
        return new Point(ChessSquare.SIZE / 2, ChessSquare.SIZE / 2);
    }

    public Point findMoveComputer() {
        Point point = new Point(0, 0);
        double scoreMax = 0;
        for (int i = 0; i < arrCaro.length; i++) {
            for (int j = 0; j < arrCaro[0].length; j++) {
                if (arrCaro[i][j] == 0) {
                    double scoreAttack = countScoreAttackVertical(i, j) + countScoreAttackHorizontal(i, j) + countScoreAttackDiagonalLeft(i, j) + countScoreAttackDiagonalRight(i, j);
                    double scoreDefend = countScoreDefendVertical(i, j) + countScoreDefendHorizontal(i, j) + countScoreDefendDiagonalLeft(i, j) + countScoreDefendDiagonalRight(i, j);

                    double scoreSum = scoreAttack + scoreDefend;
                    if (scoreMax < scoreSum) {
                        scoreMax = scoreSum;
                        point.setX(i);
                        point.setY(j);
                    }
                }
            }
        }
        return point;
    }

    public double countScoreAttack(int numberChessmanOur, int numberChessmanOur2, int numberChessmanEnemy, int numberChessmanEnemy2) {
        double score = 0;
        if (numberChessmanEnemy == 2 && numberChessmanOur < 4)
            return 0;
        if (numberChessmanOur > 4)
            numberChessmanOur = 4;
        if (numberChessmanEnemy == 0)
            score += SCORE_ATTACK[numberChessmanOur] * 2;
        else
            score += SCORE_ATTACK[numberChessmanOur];
        if (numberChessmanEnemy2 == 0)
            score += SCORE_ATTACK[numberChessmanOur2] * 2;
        else
            score += SCORE_ATTACK[numberChessmanOur2];
        if (numberChessmanOur >= numberChessmanOur2)
            score -= 1;
        else
            score -= 2;
        if (numberChessmanOur == 4)
            score *= 4;
        if (numberChessmanOur == 0)
            score += SCORE_DEFEND[numberChessmanEnemy] * 2;
        else
            score += SCORE_DEFEND[numberChessmanEnemy];
        if (numberChessmanOur2 == 0)
            score += SCORE_DEFEND[numberChessmanEnemy2] * 2;
        else
            score += SCORE_DEFEND[numberChessmanEnemy2];
        return score;
    }

    public double countScoreDefend(int numberChessmanOur, int numberChessmanOur2, int numberChessmanEnemy, int numberChessmanEnemy2) {
        double score = 0;
        if (numberChessmanOur == 2 && numberChessmanEnemy < 4)
            return 0;
        if (numberChessmanEnemy > 4)
            numberChessmanEnemy = 4;
        if (numberChessmanOur == 0)
            score += SCORE_DEFEND[numberChessmanEnemy] * 2;
        else
            score += SCORE_DEFEND[numberChessmanEnemy];
        if (numberChessmanEnemy >= numberChessmanEnemy2)
            score -= 1;
        else
            score -= 2;
        if (numberChessmanEnemy == 4)
            score *= 4;
        return score;
    }

    public double countScoreAttackVertical(int x, int y) {
        int numberChessmanOur = 0;
        int numberChessmanOur2 = 0;
        int numberChessmanEnemy = 0;
        int numberChessmanEnemy2 = 0;
        int n = arrCaro.length;
        for (int count = 1; count < 5 && x + count < n; count++) {
            if (arrCaro[x + count][y] == 2) {
                numberChessmanOur++;
            } else if (arrCaro[x + count][y] == 1) {
                numberChessmanEnemy++;
                break;
            } else {
                // = 0 không có quân cờ
                for (int count2 = 2; count2 < 6 && x + count2 < n; count2++) {
                    if (arrCaro[x + count2][y] == 2) {
                        numberChessmanOur2++;
                    } else if (arrCaro[x + count2][y] == 1) {
                        numberChessmanEnemy2++;
                        break;
                    } else
                        break;
                }
                break;
            }
        }
        for (int count = 1; count < 5 && x - count >= 0; count++) {
            if (arrCaro[x - count][y] == 2) {
                numberChessmanOur++;
            } else if (arrCaro[x - count][y] == 1) {
                numberChessmanEnemy++;
                break;
            } else {
                // = 0 là không có quân cờ
                for (int count2 = 1; count2 < 5 && x - count2 >= 0; count2++) {
                    if (arrCaro[x - count2][y] == 2) {
                        numberChessmanOur2++;
                    } else if (arrCaro[x - count2][y] == 1) {
                        numberChessmanEnemy2++;
                        break;
                    } else
                        break;
                }
                break;
            }
        }
        return countScoreAttack(numberChessmanOur, numberChessmanOur2, numberChessmanEnemy, numberChessmanEnemy2);
    }

    public double countScoreAttackHorizontal(int x, int y) {
        int numberChessmanOur = 0;
        int numberChessmanOur2 = 0;
        int numberChessmanEnemy = 0;
        int numberChessmanEnemy2 = 0;
        int m = arrCaro[0].length;
        for (int count = 1; count < 5 && y + count < m; count++) {
            if (arrCaro[x][y + count] == 2) {
                numberChessmanOur++;
            } else if (arrCaro[x][y + count] == 1) {
                numberChessmanEnemy++;
                break;
            } else {
                // = 0 không có quân cờ
                for (int count2 = 2; count2 < 6 && y + count2 < m; count2++) {
                    if (arrCaro[x][y + count2] == 2) {
                        numberChessmanOur2++;
                    } else if (arrCaro[x][y + count2] == 1) {
                        numberChessmanEnemy2++;
                        break;
                    } else
                        break;
                }
                break;
            }
        }
        for (int count = 1; count < 5 && y - count >= 0; count++) {
            if (arrCaro[x][y - count] == 2) {
                numberChessmanOur++;
            } else if (arrCaro[x][y - count] == 1) {
                numberChessmanEnemy++;
                break;
            } else {
                // = 0 là không có quân cờ
                for (int count2 = 1; count2 < 5 && y - count2 >= 0; count2++) {
                    if (arrCaro[x][y - count2] == 2) {
                        numberChessmanOur2++;
                    } else if (arrCaro[x][y - count2] == 1) {
                        numberChessmanEnemy2++;
                        break;
                    } else
                        break;
                }
                break;
            }
        }
        return countScoreAttack(numberChessmanOur, numberChessmanOur2, numberChessmanEnemy, numberChessmanEnemy2);
    }

    public double countScoreAttackDiagonalLeft(int x, int y) {
        int numberChessmanOur = 0;
        int numberChessmanOur2 = 0;
        int numberChessmanEnemy = 0;
        int numberChessmanEnemy2 = 0;
        int n = arrCaro.length;
        int m = arrCaro[0].length;
        for (int count = 1; count < 5 && x + count < n && y + count < m; count++) {
            if (arrCaro[x + count][y + count] == 2) {
                numberChessmanOur++;
            } else if (arrCaro[x + count][y + count] == 1) {
                numberChessmanEnemy++;
                break;
            } else {
                // = 0 không có quân cờ
                for (int count2 = 2; count2 < 6 && x + count2 < n && y + count2 < m; count2++) {
                    if (arrCaro[x + count2][y + count2] == 2) {
                        numberChessmanOur2++;
                    } else if (arrCaro[x + count2][y + count2] == 1) {
                        numberChessmanEnemy2++;
                        break;
                    } else
                        break;
                }
                break;
            }
        }
        for (int count = 1; count < 5 && x - count >= 0 && y - count >= 0; count++) {
            if (arrCaro[x - count][y - count] == 2) {
                numberChessmanOur++;
            } else if (arrCaro[x - count][y - count] == 1) {
                numberChessmanEnemy++;
                break;
            } else {
                // = 0 là không có quân cờ
                for (int count2 = 1; count2 < 5 && x - count2 >= 0 && y - count >= 0; count2++) {
                    if (arrCaro[x - count2][y - count2] == 2) {
                        numberChessmanOur2++;
                    } else if (arrCaro[x - count2][y - count2] == 1) {
                        numberChessmanEnemy2++;
                        break;
                    } else
                        break;
                }
                break;
            }
        }
        return countScoreAttack(numberChessmanOur, numberChessmanOur2, numberChessmanEnemy, numberChessmanEnemy2);
    }

    public double countScoreAttackDiagonalRight(int x, int y) {
        int numberChessmanOur = 0;
        int numberChessmanOur2 = 0;
        int numberChessmanEnemy = 0;
        int numberChessmanEnemy2 = 0;
        int n = arrCaro.length;
        int m = arrCaro[0].length;
        for (int count = 1; count < 5 && x - count > -0 && y + count < m; count++) {
            if (arrCaro[x - count][y + count] == 2) {
                numberChessmanOur++;
            } else if (arrCaro[x - count][y + count] == 1) {
                numberChessmanEnemy++;
                break;
            } else {
                // = 0 không có quân cờ
                for (int count2 = 2; count2 < 6 && x - count2 >= 0 && y + count2 < m; count2++) {
                    if (arrCaro[x - count2][y + count2] == 2) {
                        numberChessmanOur2++;
                    } else if (arrCaro[x - count2][y + count2] == 1) {
                        numberChessmanEnemy2++;
                        break;
                    } else
                        break;
                }
                break;
            }
        }
        for (int count = 1; count < 5 && x + count < n && y - count >= 0; count++) {
            if (arrCaro[x + count][y - count] == 2) {
                numberChessmanOur++;
            } else if (arrCaro[x + count][y - count] == 1) {
                numberChessmanEnemy++;
                break;
            } else {
                // = 0 là không có quân cờ
                for (int count2 = 1; count2 < 5 && x + count2 < n && y - count >= 0; count2++) {
                    if (arrCaro[x + count2][y - count2] == 2) {
                        numberChessmanOur2++;
                    } else if (arrCaro[x + count2][y - count2] == 1) {
                        numberChessmanEnemy2++;
                        break;
                    } else
                        break;
                }
                break;
            }
        }
        return countScoreAttack(numberChessmanOur, numberChessmanOur2, numberChessmanEnemy, numberChessmanEnemy2);
    }

    public double countScoreDefendVertical(int x, int y) {
        int numberChessmanOur = 0;
        int numberChessmanOur2 = 0;
        int numberChessmanEnemy = 0;
        int numberChessmanEnemy2 = 0;
        int n = arrCaro.length;
        for (int count = 1; count < 5 && x + count < n; count++) {
            if (arrCaro[x + count][y] == 2) {
                numberChessmanOur++;
                break;
            } else if (arrCaro[x + count][y] == 1) {
                numberChessmanEnemy++;
            } else {
                // = 0 không có quân cờ
                for (int count2 = 2; count2 < 6 && x + count2 < n; count2++) {
                    if (arrCaro[x + count2][y] == 2) {
                        numberChessmanOur2++;
                        break;
                    } else if (arrCaro[x + count2][y] == 1) {
                        numberChessmanEnemy2++;
                    } else
                        break;
                }
                break;
            }
        }
        for (int count = 1; count < 5 && x - count >= 0; count++) {
            if (arrCaro[x - count][y] == 2) {
                numberChessmanOur++;
                break;
            } else if (arrCaro[x - count][y] == 1) {
                numberChessmanEnemy++;
            } else {
                // = 0 là không có quân cờ
                for (int count2 = 1; count2 < 5 && x - count2 >= 0; count2++) {
                    if (arrCaro[x - count2][y] == 2) {
                        numberChessmanOur2++;
                        break;
                    } else if (arrCaro[x - count2][y] == 1) {
                        numberChessmanEnemy2++;
                    } else
                        break;
                }
                break;
            }
        }
        return countScoreDefend(numberChessmanOur, numberChessmanOur2, numberChessmanEnemy, numberChessmanEnemy2);
    }

    public double countScoreDefendHorizontal(int x, int y) {
        int numberChessmanOur = 0;
        int numberChessmanOur2 = 0;
        int numberChessmanEnemy = 0;
        int numberChessmanEnemy2 = 0;
        int m = arrCaro[0].length;
        for (int count = 1; count < 5 && y + count < m; count++) {
            if (arrCaro[x][y + count] == 2) {
                numberChessmanOur++;
                break;
            } else if (arrCaro[x][y + count] == 1) {
                numberChessmanEnemy++;
            } else {
                // = 0 không có quân cờ
                for (int count2 = 2; count2 < 6 && y + count2 < m; count2++) {
                    if (arrCaro[x][y + count2] == 2) {
                        numberChessmanOur2++;
                        break;
                    } else if (arrCaro[x][y + count2] == 1) {
                        numberChessmanEnemy2++;
                    } else
                        break;
                }
                break;
            }
        }
        for (int count = 1; count < 5 && y - count >= 0; count++) {
            if (arrCaro[x][y - count] == 2) {
                numberChessmanOur++;
                break;
            } else if (arrCaro[x][y - count] == 1) {
                numberChessmanEnemy++;
            } else {
                // = 0 là không có quân cờ
                for (int count2 = 1; count2 < 5 && y - count2 >= 0; count2++) {
                    if (arrCaro[x][y - count2] == 2) {
                        numberChessmanOur2++;
                        break;
                    } else if (arrCaro[x][y - count2] == 1) {
                        numberChessmanEnemy2++;
                    } else
                        break;
                }
                break;
            }
        }
        return countScoreDefend(numberChessmanOur, numberChessmanOur2, numberChessmanEnemy, numberChessmanEnemy2);
    }

    public double countScoreDefendDiagonalLeft(int x, int y) {
        int numberChessmanOur = 0;
        int numberChessmanOur2 = 0;
        int numberChessmanEnemy = 0;
        int numberChessmanEnemy2 = 0;
        int n = arrCaro.length;
        int m = arrCaro[0].length;
        for (int count = 1; count < 5 && x + count < n && y + count < m; count++) {
            if (arrCaro[x + count][y + count] == 2) {
                numberChessmanOur++;
                break;
            } else if (arrCaro[x + count][y + count] == 1) {
                numberChessmanEnemy++;
            } else {
                // = 0 không có quân cờ
                for (int count2 = 2; count2 < 6 && x + count2 < n && y + count2 < m; count2++) {
                    if (arrCaro[x + count2][y + count2] == 2) {
                        numberChessmanOur2++;
                        break;
                    } else if (arrCaro[x + count2][y + count2] == 1) {
                        numberChessmanEnemy2++;
                    } else
                        break;
                }
                break;
            }
        }
        for (int count = 1; count < 5 && x - count >= 0 && y - count >= 0; count++) {
            if (arrCaro[x - count][y - count] == 2) {
                numberChessmanOur++;
                break;
            } else if (arrCaro[x - count][y - count] == 1) {
                numberChessmanEnemy++;
            } else {
                // = 0 là không có quân cờ
                for (int count2 = 1; count2 < 5 && x - count2 >= 0 && y - count2 >= 0; count2++) {
                    if (arrCaro[x - count2][y - count2] == 2) {
                        numberChessmanOur2++;
                        break;
                    } else if (arrCaro[x - count2][y - count2] == 1) {
                        numberChessmanEnemy2++;
                    } else
                        break;
                }
                break;
            }
        }
        return countScoreDefend(numberChessmanOur, numberChessmanOur2, numberChessmanEnemy, numberChessmanEnemy2);
    }

    public double countScoreDefendDiagonalRight(int x, int y) {
        int numberChessmanOur = 0;
        int numberChessmanOur2 = 0;
        int numberChessmanEnemy = 0;
        int numberChessmanEnemy2 = 0;
        int n = arrCaro.length;
        int m = arrCaro[0].length;
        for (int count = 1; count < 5 && x - count > -0 && y + count < m; count++) {
            if (arrCaro[x - count][y + count] == 2) {
                numberChessmanOur++;
                break;
            } else if (arrCaro[x - count][y + count] == 1) {
                numberChessmanEnemy++;
            } else {
                // = 0 không có quân cờ
                for (int count2 = 2; count2 < 6 && x - count2 >= 0 && y + count2 < m; count2++) {
                    if (arrCaro[x - count2][y + count2] == 2) {
                        numberChessmanOur2++;
                        break;
                    } else if (arrCaro[x - count2][y + count2] == 1) {
                        numberChessmanEnemy2++;
                    } else
                        break;
                }
                break;
            }
        }
        for (int count = 1; count < 5 && x + count < n && y - count >= 0; count++) {
            if (arrCaro[x + count][y - count] == 2) {
                numberChessmanOur++;
                break;
            } else if (arrCaro[x + count][y - count] == 1) {
                numberChessmanEnemy++;
            } else {
                // = 0 là không có quân cờ
                for (int count2 = 1; count2 < 5 && x + count2 < n && y - count2 >= 0; count2++) {
                    if (arrCaro[x + count2][y - count2] == 2) {
                        numberChessmanOur2++;
                        break;
                    } else if (arrCaro[x + count2][y - count2] == 1) {
                        numberChessmanEnemy2++;
                    } else
                        break;
                }
                break;
            }
        }
        return countScoreDefend(numberChessmanOur, numberChessmanOur2, numberChessmanEnemy, numberChessmanEnemy2);
    }
}
