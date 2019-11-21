package aqing.designMode.template;

/**
 * @Description:模板模式简单实现：总逻辑接口
 * @Author: fuqi
 * @Date: 2019/11/21 上午11:38
 */
public abstract class AbstractPerson implements Person{

    public static  String CANEAT_FOOD = "IBM<21，food is health";
    @Override
    public boolean eat(String food){
        System.out.println("AbstractPerson.eat");
        if(checktCanEat(food)){
            return true;
        }
        throw new IllegalStateException("food cann't be eaten");
    }
}
