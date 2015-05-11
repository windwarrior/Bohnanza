/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.bpsd.exceptions;

// Thrown when things are not configured as they should be
public class ImproperlyConfiguredException extends RuntimeException {

    public ImproperlyConfiguredException(String msg) {
        super(msg);
    }
    
}
