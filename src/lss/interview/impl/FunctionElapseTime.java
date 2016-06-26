package lss.interview.impl;

import java.util.ArrayList;
import java.util.List;

/**
main(){
    f1(f2(f3()))
}
Input: 
a sequential list of logs, each item contains function name and its timestamp. 
Essentially it's function call stack
<p>
f1 enter t1 1.1, at t1 function f1 enters
f2 enter t2 1.3, at t2 function f2 enters
f3 enter t3 1.5
f3 exit t4 2.2
f2 exit t5 2.3
f4 enter t6 2.5
f4 exit t7 2.8
f1 exit t8 3.3, at t8 function f1 exits.

Output:
Given a function name, return it's elapse time.
i.e. f1, (f2 starttime - f1 starttime) + (f4 endtime - f1 endtime) = t2-t1 + t8-t7
<p>
@author bruce
*/

public class FunctionElapseTime {

    static class Log{
        String funcName;
        double time;
        public Log(String name, double time) {
            this.funcName = name;
            this.time = time;
        }
    }
    public static void main(String[] args) {
        List<Log> input = new ArrayList<>();
        input.add(new Log("f1", 1.1 ));
        input.add(new Log("f2", 1.3 ));
        input.add(new Log("f3", 1.5 ));
        input.add(new Log("f3", 2.2 ));
        input.add(new Log("f2", 2.3 ));
        input.add(new Log("f4", 2.5 ));
        input.add(new Log("f4", 2.8 ));
        input.add(new Log("f1", 3.3 ));
        System.out.println(2.2-1.5);
        System.out.println(exclusive_time(input, "f3"));
    }
    /**
     * To get a function's elapse time, two things need to know:
     * 1. function's start time and end time
     * 2. Direct called functions starttime and endtime
     * <p>
     * Time: O(n)
     * Space: Constant
     * */
    private static double exclusive_time(List<Log> logs, String function_name) {
        if(logs.size()<=0) return 0.0;
        double res = 0.0;
        boolean first = true;
        for(int i=0;i<logs.size();i++) {
            if(function_name.equals(logs.get(i).funcName)) {
                if(first && i+1<logs.size()) {
                    first = false;
                    res = logs.get(i+1).time - logs.get(i).time;
                }else if (!first) {
                    if(logs.get(i).funcName.equals(logs.get(i-1).funcName)) return res;
                    res += logs.get(i).time - logs.get(i-1).time;
                    return res;
                }
            }
        }
        return res;
    }
}
