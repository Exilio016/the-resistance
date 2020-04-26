package com.brunoflaviof.game;

import java.math.BigInteger;
import java.util.Stack;

public class Ack {

    BigInteger ack(int a, int b){
        Stack<AckParameters> stack = new Stack<>();
        stack.push(new AckParameters(BigInteger.valueOf(a), BigInteger.valueOf(b)));

        BigInteger response = BigInteger.ZERO;
        BigInteger x, y;

        do {
            if (isZero(x)) {
                response = BigInteger.ONE.add(y);
            }else if(isZero(y)){
                stack.push(new AckParameters(x.subtract(BigInteger.ONE), BigInteger.ONE));
            }else {
                
            }
        }while (!stack.empty());

        return response;
    }

    private boolean isZero(BigInteger i){
        return i.compareTo(BigInteger.ZERO) == 0;
    }

    private class AckParameters{
        BigInteger x;
        BigInteger y;

        AckParameters(BigInteger x, BigInteger y){
            this.x = x;
            this.y = y;
        }
    }
}
