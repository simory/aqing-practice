package aqing.designMode.behaviourStrategy.chainOfResponsobilityPattern.template;

/**
 * @Description:女性吃食物
 * @Author: fuqi
 * @Date: 2019/11/21 上午11:42
 */
public class Female extends AbstractPerson implements Person {


    @Override
    public boolean eat(String food){
        System.out.println("Female.eat");
        return super.eat(food);
    }

    @Override
    public boolean checktCanEat(String food) {
        System.out.println("Female.checktCanEat");
        if(food.equals(CANEAT_FOOD)){
            return true;
        }
        return false;
    }
}
