package cn.edu.gdmec.android.boxuegu.bean;

/**
 * Created by Jack on 2017/12/26.
 * 练习实体类
 */

public class ExercisesBean {
    public int id;//每章节id
    public String title;//每章习题标题
    public String content;//每章习题的数目
    public int background;//每章习前边的序号背景
    public int subjectId;//每章习题的Id
    public String subject;//每章习题的题干
    public String a;//每道题的A选项
    public String b;//每道题的B选项
    public String c;//每道题的C选项
    public String d;//每道题的D选项
    public int answer;//每道题的正确答案
    public int select;//用户选中的选项（0表示选项对了，1表示A选项错了，2表示B选项错了，3表示C选项错了，4表示D选项错了）
}
