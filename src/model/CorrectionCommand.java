/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author donatdeva
 */
public enum CorrectionCommand {
    APPLY, //implies stepback and apply afterwards
    TRYLEFT,
    TRYRIGHT,
    TRYUP,
    TRYDOWN,
    STEPBACK
}
