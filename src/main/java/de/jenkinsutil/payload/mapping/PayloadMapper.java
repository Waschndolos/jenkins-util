package de.jenkinsutil.payload;

public interface PayloadMapper<I,T> {

    I convert(T input);
}
