/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nyxcode.compilador.controls;

import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

/**
 *
 * @author DocSe
 */
public class EjempMap {

    public static void main(String[] args) {
        Map<Integer, String> map = new TreeMap<Integer, String>();
        map.put(10, "Const");
        map.put(33, "Omar");
        System.out.println(map.keySet());
        System.out.println(map.values());
        map.put(33, "Omar");
        System.out.println(map.values());
        map.put(5, "Xavier");
        System.out.println(map.values());
        Stack<String> s = new Stack<>();
        s.push("Alexia");
        System.out.println(s.pop());
    }

}
