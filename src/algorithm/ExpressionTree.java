package algorithm;

import ds.LinkedBinaryTree;
import ds.Position;
import javafx.util.Pair;

import java.util.*;

/**
 * 表达式树
 */
public class ExpressionTree extends LinkedBinaryTree<String> {
    private Map<String, Double> variables = new HashMap<>();

    /*
     * Map<运算符名称, Pair<优先级, 几元运算符>
     * 注意一元运算符的中缀表示为后缀
     */
    private static Map<String, Pair<Integer, Integer>> operatorMap = new HashMap<>();

    static {
        operatorMap.put("+", new Pair<>(1, 2)); // 加法
        operatorMap.put("-", new Pair<>(1, 2)); // 减法
        operatorMap.put("*", new Pair<>(2, 2)); // 乘法
        operatorMap.put("/", new Pair<>(2, 2)); // 除法
        operatorMap.put("~", new Pair<>(4, 1)); // 取反
        operatorMap.put("^", new Pair<>(3, 2)); // 幂
        operatorMap.put("@", new Pair<>(4, 1)); // 绝对值
        operatorMap.put("%", new Pair<>(2, 2)); // 取模
        operatorMap.put("#", new Pair<>(2, 2)); // 整除
        operatorMap.put("!", new Pair<>(4, 1)); // 阶乘
    }

    {
        variables.put("pi", 3.14159265358979323846);
        variables.put("e", 2.7182818284590452354);
    }

    public ExpressionTree() {
    }

    public ExpressionTree(String s) {
        super(s);
    }

    public double eval(Position<String> p) {
        if (isExternal(p)) {
            try {
                if (variables.containsKey(p.getElement())) {
                    return variables.get(p.getElement());
                } else {
                    return Double.parseDouble(p.getElement());
                }
            } catch (NumberFormatException e) {
                throw new NumberFormatException("叶节点" + p.getElement() + "存储的不是浮点数或者是未赋值的变量");
            }
        }
        switch (p.getElement()) {
            case "+":
                return eval(left(p)) + eval(right(p));
            case "-":
                return eval(left(p)) - eval(right(p));
            case "*":
                return eval(left(p)) * eval(right(p));
            case "/":
                return eval(left(p)) / eval(right(p));
            case "~":
                return -eval(left(p));
            case "^":
                return Math.pow(eval(left(p)), eval(right(p)));
            case "@":
                return Math.abs(eval(left(p)));
            case "%":
                return eval(left(p)) % eval(right(p));
            case "#":
                return Math.floor(eval(left(p)) / eval(right(p)));
            case "!":
                double temp = eval(left(p));
                int val = (int) temp;
                if (val != temp || val <= 0) {
                    throw new ArithmeticException("只有正整数才能进行阶乘运算");
                }
                int retVal = val;
                for (int i = 2; i < val; i++) {
                    retVal *= i;
                }
                return retVal;
            default:
                throw new ArithmeticException("节点" + p.getElement() + "中存储的不是正确的运算符");
        }
    }

    private static List<String> split(String expression, boolean reverse) {
        StringTokenizer st = new StringTokenizer(expression, "[() +\\-*/~^@%#!]", true);
        LinkedList<String> lst = new LinkedList<>();
        while (st.hasMoreTokens()) {
            String expr = st.nextToken();
            if (!expr.equals(" ")) {
                if (!reverse) {
                    lst.addLast(expr);
                } else {
                    lst.addFirst(expr);
                }
            }
        }
        return lst;
    }

    private static void mergeNode(String expr, Stack<ExpressionTree> stack, String s, boolean reverse) {
        ExpressionTree treeRoot = new ExpressionTree(expr);
        try {
            if (operatorMap.get(expr).getValue() == 2) {
                ExpressionTree treeLeft = stack.pop();
                ExpressionTree treeRight = stack.pop();
                if (reverse) {
                    treeRoot.attach(treeRoot.root(), treeLeft, treeRight);
                } else {
                    treeRoot.attach(treeRoot.root(), treeRight, treeLeft);
                }
            } else {
                treeRoot.attach(treeRoot.root(), stack.pop(), new ExpressionTree());
            }
        } catch (EmptyStackException e) {
            System.out.println(expr);
            throw new ArithmeticException("无效的表达式" + s);
        }
        stack.push(treeRoot);
    }

    public static ExpressionTree fromPrefix(String prefix) {
        Stack<ExpressionTree> stack = new Stack<>();
        for (String expr : split(prefix, true)) {
            if (operatorMap.containsKey(expr)) {
                mergeNode(expr, stack, prefix, true);
            } else {
                stack.push(new ExpressionTree(expr));
            }
        }
        if (stack.size() != 1) {
            throw new ArithmeticException("无效的表达式" + prefix);
        }
        return stack.pop();
    }

    public static ExpressionTree fromInfix(String infix) {
        Stack<ExpressionTree> stack = new Stack<>();
        Stack<String> operators = new Stack<>();
        for (String expr : split(infix, false)) {
            if (operatorMap.containsKey(expr)) {
                while (!operators.isEmpty()) {
                    String operator = operators.peek();
                    if ("(".equals(operator) || operatorMap.get(expr).getKey() > operatorMap.get(operator).getKey())
                        break;
                    mergeNode(operators.pop(), stack, infix, false);
                }
                operators.push(expr);
            } else if ("(".equals(expr)) {
                operators.push("(");
            } else if (")".equals(expr)) {
                while (!operators.isEmpty()) {
                    String operator = operators.pop();
                    if (!operator.equals("(")) {
                        mergeNode(operator, stack, infix, false);
                    } else {
                        break;
                    }
                }
            } else {
                stack.add(new ExpressionTree(expr));
            }
        }
        while (!operators.isEmpty()) {
            mergeNode(operators.pop(), stack, infix, false);
        }
        return stack.pop();
    }

    public static ExpressionTree fromPostfix(String postfix) {
        Stack<ExpressionTree> stack = new Stack<>();
        for (String expr : split(postfix, false)) {
            if (operatorMap.containsKey(expr)) {
                mergeNode(expr, stack, postfix, false);
            } else {
                stack.push(new ExpressionTree(expr));
            }
        }
        if (stack.size() != 1) {
            throw new ArithmeticException("无效的表达式" + postfix);
        }
        return stack.pop();
    }

    public Iterable<Map.Entry<String, Double>> getVariables() {
        return variables.entrySet();
    }

    public double getVariable(String variable) {
        try {
            return variables.get(variable);
        } catch (NullPointerException e) {
            throw new NullPointerException("变量" + variable + "未赋值");
        }
    }

    public void setVariable(String variable, double value) {
        variables.put(variable, value);
    }

    public double eval() {
        return eval(root());
    }

    public String toPrefix() {
        StringBuilder sb = new StringBuilder();
        preorder().forEach(i -> sb.append(i.getElement()).append(" "));
        return sb.toString();
    }

    public String toInfix() {
        StringBuilder s = new StringBuilder();
        infixSubtree(root(), s);
        return s.toString();
    }

    private void infixSubtree(Position<String> p, StringBuilder s) {
        String e = p.getElement();
        if (isExternal(p)) {
            s.append(e);
        } else {
            if (operatorMap.get(e).getValue() == 2) {
                s.append("(");
                infixSubtree(left(p), s);
                s.append(" ").append(e).append(" ");
                infixSubtree(right(p), s);
                s.append(")");
            } else {
                infixSubtree(left(p), s);
                s.append(e);
            }
        }
    }

    public String toPostfix() {
        StringBuilder sb = new StringBuilder();
        postorder().forEach(i -> sb.append(i.getElement()).append(" "));
        return sb.toString();
    }
}
