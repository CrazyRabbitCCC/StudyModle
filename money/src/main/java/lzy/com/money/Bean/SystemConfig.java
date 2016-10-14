package lzy.com.money.Bean;// @author: lzy  time: 2016/09/02.

public class SystemConfig {
    private boolean window, resident, timer, password, onlyHome;
    private String timer1, timer2, numPW, picPW,passwordType="0", picPath, picWord, wordPW;

    public SystemConfig(){
          window=true;
         resident=true;
         timer=false;
         password=false;
         onlyHome=false;

    }


    public Boolean getSystemConfig(int num) {
        switch (num) {
            case 0:
                return window;
            case 1:
                return resident;
            case 2:
                return timer;
            case 3:
                return password;
            case 4:
                return onlyHome;
            default:
                break;

        }
        return false;
    }

    public String getSystemConfig(String config) {
        switch (config) {
            case "3":
                return passwordType;
            case "2-1":
                return timer1;
            case "3-1":
                return numPW;
            case "2-2":
                return timer2;
            case "3-2":
                return picPW;
            case "3-3-1":
                return picPath;
            case "3-3-2":
                return picWord;
            case "3-3-3":
                return wordPW;
            default:
                break;

        }
        return null;
    }

    public void setSystemConfig(int num, Boolean result) {
        switch (num) {
            case 0:
                window=result;
                break;
            case 1:
                resident=result;
                break;
            case 2:
                timer=result;
                break;
            case 3:
                password=result;
                break;
            case 4:
                onlyHome=result;
                break;
            default:
                break;
        }
    }

    public void setSystemConfig(String config, String result) {
        switch (config) {
            case "3":
                passwordType=result;
                break;
            case "2-1":
                timer1 = result;
                break;
            case "3-1":
                numPW = result;
                break;
            case "2-2":
                timer2 = result;
                break;
            case "3-2":
                picPW = result;
                break;
            case "3-3-1":
                picPath = result;
                break;
            case "3-3-2":
                picWord = result;
                break;
            case "3-3-3":
                wordPW = result;
                break;
            default:
                break;
        }
    }
}
