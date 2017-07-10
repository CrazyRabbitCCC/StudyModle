package study.lzy.studymodle.gomoku;

import android.graphics.Point;

/**
 * @author Gavin
 * @date 2017/06/28.
 */

public class GomokuAI {

    private Boolean white;
    private GomokuUtil gomokuUtil;
    private int AICardNum;
    private int peopleNum;

    public GomokuAI(GomokuUtil gomokuUtil, boolean white) {
        this.gomokuUtil = gomokuUtil;
        this.white = white;
        AICardNum = white ? -1 : 1;
        peopleNum = white ? 1 : -1;
    }

    public void play() {
        GomokuCard[][] cards = gomokuUtil.getCards();
        int i = (int) (Math.random() * GomokuUtil.WIDTH);
        int j = (int) (Math.random() * GomokuUtil.WIDTH);
        if (cards[i][j].getNum() == 0) {
            cards[i][j].setNum(AICardNum);
            gomokuUtil.setWhite(!white);
        } else play();
    }

    public void play2() {
        final GomokuCard[][] cards = gomokuUtil.getCards();


    }

    private int getPeoplePalceImportant(GomokuCard card) {
        if (card.getNum() != 0)
            return 0;

        return 1;
    }

    private int getPiecesPlaceImportant(GomokuCard card, int num, int hisNum) {
        if (card.getNum() != 0)
            return 0;
        GomokuCard[][] cards = gomokuUtil.getCards();
        PieceLine row = getPieceLine(Direction.ROW, card, num);
        PieceLine line = getPieceLine(Direction.LINE, card, num);
        PieceLine upl2lowr = getPieceLine(Direction.UoL2LowR, card, num);
        PieceLine upr2lowl = getPieceLine(Direction.UpR2LowL, card, num);
        Situation situation = new Situation();
        setSituation(situation, row, num, hisNum);
        setSituation(situation, line, num, hisNum);
        setSituation(situation, upl2lowr, num, hisNum);
        setSituation(situation, upr2lowl, num, hisNum);

        return 1;
    }

    private PieceLine getPieceLine(Direction direction, GomokuCard card, int num) {
        GomokuCard[][] cards = gomokuUtil.getCards();
        PieceLine row = new PieceLine(Direction.ROW, 1, card.getPoint(), card.getPoint());
        while (row.before(1) != null && (cards[row.before(1).x][row.before(1).y].getNum() == num)) {
            row.setStartPoint(row.before(1));
            row.setChessNum(row.getChessNum() + 1);
        }
        while (row.after(1) != null && (cards[row.after(1).x][row.after(1).y].getNum() == num)) {
            row.setEndPoint(row.after(1));
            row.setChessNum(row.getChessNum() + 1);
        }

        return row;
    }

    class Situation {
        int win5 = 0, live4 = 0, dead4 = 0, live3 = 0, dead3 = 0, live2 = 0, dead2 = 0;

        public Situation() {
        }

        public void addSituation(int num, boolean live) {
            switch (num) {
                case 5:
                    win5++;
                    break;
                case 4:
                    if (live)
                        live4++;
                    else dead4++;
                    break;
                case 3:
                    if (live)
                        live3++;
                    else dead3++;
                    break;
                case 2:
                    if (live)
                        live2++;
                    else dead2++;
                    break;
                default:
                    break;

            }
        }

        public PiecesType getPiecesType() {
            if (win5 >= 1)
                return PiecesType.LIVE5;//赢5

            if (live4 >= 1 || dead4 >= 2 || (dead4 >= 1 && live3 >= 1))
                return PiecesType.Leveltwo;//活4 双死4 死4活3

            if (live3 >= 2)
                return PiecesType.DOUBLE_LIVE3;//双活3

            if (dead3 >= 1 && live3 >= 1)
                return PiecesType.DEAD3_LIVE3;//死3活3

            if (dead4 >= 1)
                return PiecesType.DEAD4;//死4

            if (live3 >= 1)
                return PiecesType.LiVE3;//单活3

            if (live2 >= 2)
                return PiecesType.DOULE_LIVE2;//双活2

            if (live2 >= 1)
                return PiecesType.LIVE2;//活2

            if (dead3 >= 1)
                return PiecesType.DEAD3;//跳活3

            if (dead2 >= 1)
                return PiecesType.DEAD2;//死2

            return PiecesType.NOTHING;//没有威胁
        }
    }

    private void setSituation(Situation situation, PieceLine line, int num, int hisNum) {
        GomokuCard[][] cards = gomokuUtil.getCards();
        int count = line.getChessNum();

        if (count >= 5) {
            situation.addSituation(5, true);
        } else if (count == 4)//中心线4连
        {
            Point b1 = line.before(1);
            Point a1 = line.after(1);

            if (b1 != null && cards[b1.x][b1.y].getNum() == 0 && a1 != null && cards[a1.x][a1.y].getNum() == 0) {//两边断开位置均空
                situation.addSituation(4, true);//活四
            } else if ((b1 == null || cards[b1.x][b1.y].getNum() == hisNum) && (a1 == null || cards[a1.x][a1.y].getNum() == hisNum)) {//两边断开位置均非空
                return;//没有威胁
            } else //两边断开位置只有一个空
                situation.addSituation(4, false);
        } else if (count == 3) {//中心线3连
            Point b1 = line.before(1);
            Point b2 = line.before(2);
            Point a1 = line.after(1);
            Point a2 = line.after(2);
            if (b1 != null && cards[b1.x][b1.y].getNum() == 0 && a1 != null && cards[a1.x][a1.y].getNum() == 0)//两边断开位置均空
            {
                if ((b2 == null || cards[b2.x][b2.y].getNum() == hisNum) && (a2 == null || cards[a2.x][a2.y].getNum() == hisNum))//均为对手棋子
                    situation.addSituation(3, false);
                else if (b2 != null && cards[b2.x][b2.y].getNum() == num || a2 != null && cards[a2.x][a2.y].getNum() == num)//只要一个为自己的棋子
                    situation.addSituation(4, false);
                else if ((b2 != null && cards[b2.x][b2.y].getNum() == 0) || (a2 != null && cards[a2.x][a2.y].getNum() == 0))//只要有一个空
                    situation.addSituation(3, true);

            } else if ((b1 == null || cards[b1.x][b1.y].getNum() == hisNum) && (a1 == null || cards[a1.x][a1.y].getNum() == hisNum))//两边断开位置均非空
            {
                //没有威胁
            } else if (b1 != null && cards[b1.x][b1.y].getNum() == 0 || a1 != null && cards[a1.x][a1.y].getNum() == 0)//两边断开位置只有一个空
            {

                if ((b1 == null || cards[b1.x][b1.y].getNum() == hisNum)) {//左边被对方堵住
                    if ((a2 == null || cards[a2.x][a2.y].getNum() == hisNum))//右边也被对方堵住
                        return;
                    else if ((cards[a2.x][a2.y].getNum() == 0))//右边均空
                        situation.addSituation(3, false);
                    else if ((cards[a2.x][a2.y].getNum() == num))
                        situation.addSituation(4, false);

                } else if ((a1 == null || cards[a1.x][a1.y].getNum() == hisNum)) {//右边被对方堵住
                    if ((b2 != null && cards[b2.x][b2.y].getNum() == 0))//左边均空
                        situation.addSituation(3, false);
                    if ((b2 != null && cards[b2.x][b2.y].getNum() == num))//左边还有自己的棋子
                        situation.addSituation(4, false);
                }
            }
        } else if (count == 2) {//中心线2连
            Point b1 = line.before(1);
            Point b2 = line.before(2);
            Point a1 = line.after(1);
            Point a2 = line.after(2);
            Point b3 = line.before(3);
            Point a3 = line.after(3);
            if (b1 != null && cards[b1.x][b1.y].getNum() == 0 && a1 != null && cards[a1.x][a1.y].getNum() == 0)//两边断开位置均空
            {
                if (((a2 != null && cards[a2.x][a2.y].getNum() == 0) && a3 != null && cards[a3.x][a3.y].getNum() == num) ||
                        ((b2 != null && cards[b2.x][b2.y].getNum() == 0) && b3 != null && cards[b3.x][b3.y].getNum() == num))
                    situation.addSituation(3, false);//死3
                else if ((b2 != null && cards[b2.x][b2.y].getNum() == 0) && (a2 != null && cards[a2.x][a2.y].getNum() == 0))
                    situation.addSituation(2, true);//活2
                else if (((a2 != null && cards[a2.x][a2.y].getNum() == num) && (b3 == null || cards[b3.x][b3.y].getNum() == hisNum)) ||
                        ((b2 != null && cards[b2.x][b2.y].getNum() == num) && (b3 == null || cards[b3.x][b3.y].getNum() == hisNum)))
                    situation.addSituation(3, false);//死3

                else if (((a2 != null && cards[a2.x][a2.y].getNum() == num) && a3 != null && cards[a3.x][a3.y].getNum() == num) ||
                        ((b2 != null && cards[b2.x][b2.y].getNum() == num) && b3 != null && cards[b3.x][b3.y].getNum() == num))
                    situation.addSituation(4, false);//死4

                else if (((a2 != null && cards[a2.x][a2.y].getNum() == num) && (a3 != null && cards[a3.x][a3.y].getNum() == 0)) ||
                        ((b2 != null && cards[b2.x][b2.y].getNum() == num) && (b3 != null && cards[b3.x][b3.y].getNum() == 0)))
                    situation.addSituation(3, true);//跳活3
                //其他情况在下边返回NOTHREAT
            } else if ((b1 == null || cards[b1.x][b1.y].getNum() == hisNum) && (a1 == null || cards[a1.x][a1.y].getNum() == hisNum))//两边断开位置均非空
            {
                ;
            } else if (b1 != null && cards[b1.x][b1.y].getNum() == 0 || a1 != null && cards[a1.x][a1.y].getNum() == 0)//两边断开位置只有一个空
            {
                if ((b1 == null || cards[b1.x][b1.y].getNum() == hisNum)) {//左边被对方堵住
                    if ((a2 == null || cards[a2.x][a2.y].getNum() == hisNum) || (a3 == null || cards[a3.x][a3.y].getNum() == hisNum)) {//只要有对方的一个棋子
                        ;//没有威胁
                    } else if ((a2 != null && cards[a2.x][a2.y].getNum() == 0) && (a3 != null && cards[a3.x][a3.y].getNum() == 0)) {//均空
                        situation.addSituation(2, false);//死2
                    } else if ((a2 != null && cards[a2.x][a2.y].getNum() == num) && a3 != null && cards[a3.x][a3.y].getNum() == num) {//均为自己的棋子
                        situation.addSituation(4, false);//死4
                    } else if ((a2 != null && cards[a2.x][a2.y].getNum() == num) || a3 != null && cards[a3.x][a3.y].getNum() == num) {//只有一个自己的棋子
                        situation.addSituation(3, false);//死3
                    }
                } else if ((a1 == null || cards[a1.x][a1.y].getNum() == hisNum)) {//右边被对方堵住
                    if ((b2 == null || cards[b2.x][b2.y].getNum() == hisNum) || (b3 == null || cards[b3.x][b3.y].getNum() == hisNum)) {//只要有对方的一个棋子
                        ;//没有威胁
                    } else if ((b2 != null && cards[b2.x][b2.y].getNum() == 0) && (b3 != null && cards[b3.x][b3.y].getNum() == 0)) {//均空
                        situation.addSituation(2, false);//死2
                    } else if ((b2 != null && cards[b2.x][b2.y].getNum() == num) && b3 != null && cards[b3.x][b3.y].getNum() == num) {//均为自己的棋子
                        situation.addSituation(4, false);//死4
                    } else if ((b2 != null && cards[b2.x][b2.y].getNum() == num) || b3 != null && cards[b3.x][b3.y].getNum() == num) {//只有一个自己的棋子
                        situation.addSituation(3, false);//死3
                    }
                }
            }
        } else if (count == 1) {//中心线1连
            Point b1 = line.before(1);
            Point b2 = line.before(2);
            Point a1 = line.after(1);
            Point a2 = line.after(2);
            Point b3 = line.before(3);
            Point a3 = line.after(3);
            Point b4 = line.before(3);
            Point a4 = line.after(3);

            if (b1 != null && cards[b1.x][b1.y].getNum() == 0 && (b2 != null && cards[b2.x][b2.y].getNum() == num) &&
                    b3 != null && cards[b3.x][b3.y].getNum() == num && b4 != null && cards[b4.x][b4.y].getNum() == num)
                situation.addSituation(4, false);
            else if (a1 != null && cards[a1.x][a1.y].getNum() == 0 && (a2 != null && cards[a2.x][a2.y].getNum() == num) &&
                    a3 != null && cards[a3.x][a3.y].getNum() == num && a4 != null && cards[a4.x][a4.y].getNum() == num)
                situation.addSituation(4, false);

            else if (b1 != null && cards[b1.x][b1.y].getNum() == 0 && (b2 != null && cards[b2.x][b2.y].getNum() == num) &&
                    b3 != null && cards[b3.x][b3.y].getNum() == num && b4 != null && cards[b4.x][b4.y].getNum() == 0 && a1 != null && cards[a1.x][a1.y].getNum() == 0)
                situation.addSituation(3, true);
            else if (a1 != null && cards[a1.x][a1.y].getNum() == 0 && (a2 != null && cards[a2.x][a2.y].getNum() == num) &&
                    a3 != null && cards[a3.x][a3.y].getNum() == num && a4 != null && cards[a4.x][a4.y].getNum() == 0 && b1 != null && cards[b1.x][b1.y].getNum() == 0)
                situation.addSituation(3, true);

            else if (b1 != null && cards[b1.x][b1.y].getNum() == 0 && (b2 != null && cards[b2.x][b2.y].getNum() == num) &&
                    b3 != null && cards[b3.x][b3.y].getNum() == num && (b4 == null || cards[b4.x][b4.y].getNum() == hisNum) && a1 != null && cards[a1.x][a1.y].getNum() == 0)
                situation.addSituation(3, false);
            else if (a1 != null && cards[a1.x][a1.y].getNum() == 0 && (a2 != null && cards[a2.x][a2.y].getNum() == num) &&
                    a3 != null && cards[a3.x][a3.y].getNum() == num && (a4 == null || cards[a4.x][a4.y].getNum() == hisNum) && b1 != null && cards[b1.x][b1.y].getNum() == 0)
                situation.addSituation(3, false);

            else if (b1 != null && cards[b1.x][b1.y].getNum() == 0 && (b2 != null && cards[b2.x][b2.y].getNum() == 0) &&
                    b3 != null && cards[b3.x][b3.y].getNum() == num && b4 != null && cards[b4.x][b4.y].getNum() == num)
                situation.addSituation(3, false);
            else if (a1 != null && cards[a1.x][a1.y].getNum() == 0 && (a2 != null && cards[a2.x][a2.y].getNum() == 0) &&
                    a3 != null && cards[a3.x][a3.y].getNum() == num && a4 != null && cards[a4.x][a4.y].getNum() == num)
                situation.addSituation(3, false);

            else if (b1 != null && cards[b1.x][b1.y].getNum() == 0 && (b2 != null && cards[b2.x][b2.y].getNum() == num) &&
                    (b3 != null && cards[b3.x][b3.y].getNum() == 0) && b4 != null && cards[b4.x][b4.y].getNum() == num)
                situation.addSituation(3, false);
            else if (a1 != null && cards[a1.x][a1.y].getNum() == 0 && (a2 != null && cards[a2.x][a2.y].getNum() == num) &&
                    (a3 != null && cards[a3.x][a3.y].getNum() == 0) && a4 != null && cards[a4.x][a4.y].getNum() == num)
                situation.addSituation(3, false);

            else if (b1 != null && cards[b1.x][b1.y].getNum() == 0 && (b2 != null && cards[b2.x][b2.y].getNum() == num) &&
                    (b3 != null && cards[b3.x][b3.y].getNum() == 0) && b4 != null && cards[b4.x][b4.y].getNum() == 0 && a1 != null && cards[a1.x][a1.y].getNum() == 0)
                situation.addSituation(2, true);
            else if (a1 != null && cards[a1.x][a1.y].getNum() == 0 && (a2 != null && cards[a2.x][a2.y].getNum() == num) &&
                    (a3 != null && cards[a3.x][a3.y].getNum() == 0) && a4 != null && cards[a4.x][a4.y].getNum() == 0 && b1 != null && cards[b1.x][b1.y].getNum() == 0)
                situation.addSituation(2, true);

            else if (b1 != null && cards[b1.x][b1.y].getNum() == 0 && (b2 != null && cards[b2.x][b2.y].getNum() == 0) &&
                    b3 != null && cards[b3.x][b3.y].getNum() == num && b4 != null && cards[b4.x][b4.y].getNum() == 0 && a1 != null && cards[a1.x][a1.y].getNum() == 0)
                situation.addSituation(2, true);
            else if (a1 != null && cards[a1.x][a1.y].getNum() == 0 && (a2 != null && cards[a2.x][a2.y].getNum() == 0) &&
                    a3 != null && cards[a3.x][a3.y].getNum() == num && a4 != null && cards[a4.x][a4.y].getNum() == 0 && b1 != null && cards[b1.x][b1.y].getNum() == 0)
                situation.addSituation(2, true);
            //其余在下边返回没有威胁
        }
        ;//返回没有威胁

    }

    class PieceLine {
        Direction direction;
        int chessNum;
        Point startPoint, endPoint;

        public PieceLine(Direction direction) {
            this.direction = direction;
        }

        public PieceLine(Direction direction, int chessNum, Point startPoint, Point endPoint) {
            this.direction = direction;
            this.chessNum = chessNum;
            this.startPoint = startPoint;
            this.endPoint = endPoint;
        }

        public Point before(int num) {
            int x = startPoint.x;
            int y = startPoint.y;
            switch (direction) {
                case ROW:
                    if (x - num >= 0)
                        return new Point(x - num, y);
                    else return null;
                case LINE:
                    if (y - num > -1)
                        return new Point(x, y - num);
                    else
                        return null;
                case UoL2LowR:
                    if (x - num > -1 && y - num > -1)
                        return new Point(x, y - num);
                    else return null;
                default:
                    if (x + num < GomokuUtil.WIDTH && y - num > -1)
                        return new Point(x, y - num);
                    else return null;
            }
        }

        public Point after(int num) {
            int x = endPoint.x;
            int y = endPoint.y;
            switch (direction) {
                case ROW:
                    if (x + num < GomokuUtil.WIDTH)
                        return new Point(x + num, y);
                    else return null;
                case LINE:
                    if (y + num < GomokuUtil.WIDTH)
                        return new Point(x, y + num);
                    else
                        return null;
                case UoL2LowR:
                    if (x + num < GomokuUtil.WIDTH && y + num < GomokuUtil.WIDTH)
                        return new Point(x, y + num);
                    else return null;
                default:
                    if (x - num < GomokuUtil.WIDTH && y + num < GomokuUtil.WIDTH)
                        return new Point(x, y + num);
                    else return null;
            }
        }


        public Direction getDirection() {
            return direction;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        public int getChessNum() {
            return chessNum;
        }

        public void setChessNum(int chessNum) {
            this.chessNum = chessNum;
        }

        public Point getStartPoint() {
            return startPoint;
        }

        public void setStartPoint(Point startPoint) {
            this.startPoint = startPoint;
        }

        public Point getEndPoint() {
            return endPoint;
        }

        public void setEndPoint(Point endPoint) {
            this.endPoint = endPoint;
        }
    }

    enum Direction {
        ROW, LINE, UoL2LowR, UpR2LowL
    }


    enum PiecesType {
        LIVE5(10000),
        Leveltwo(10000),
        LIVE4(10000),
        DOUBLE_DEAD4(10000),
        DEAD4_LIVE3(10000),
        DOUBLE_LIVE3(5000),
        DEAD3_LIVE3(1000),
        DEAD4(500),
        LiVE3(100),
        DOULE_LIVE2(50),
        LIVE2(10),
        DEAD3(5),
        DEAD2(2),
        NOTHING(1);

        int important;

        PiecesType(int important) {
            this.important = important;
        }

        public int getImportant() {
            return important;
        }
    }


}

