package com.googlecode.FOandW;

import java.awt.Toolkit;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.swing.*;
import javax.swing.text.*;

class SignedNumericField extends JTextField
{
    private Toolkit toolkit;
    private NumberFormat integerFormatter;

    SignedNumericField(int value, int columns)
    {
        super(columns);
        toolkit = Toolkit.getDefaultToolkit();
        integerFormatter = NumberFormat.getNumberInstance(Locale.US);
        integerFormatter.setParseIntegerOnly(true);
        setValue(value);
    }

    public int getValue()
    {
        int retVal = 0;
        try {
            retVal = integerFormatter.parse(getText()).intValue();
        } catch (ParseException e) {
            // This should never happen because insertString allows
            // only properly formatted data to get in the field.
            toolkit.beep();
        }
        return retVal;
    }

    public void setValue(int value)
    {
        setText(integerFormatter.format(value));
    }

    protected Document createDefaultModel()
    {
        return new SignedNumericDocument();
    }

    protected class SignedNumericDocument extends PlainDocument
    {
        public void insertString(int offs, 
                                 String str,
                                 AttributeSet a) 
                throws BadLocationException {
            char[] source = str.toCharArray();
            char[] result = new char[source.length];
            int j = 0;

            for (int i = 0; i < result.length; i++) {
                if ((Character.isDigit(source[i]))
                    || ((i==0) && (source[i] == '-')))
                    result[j++] = source[i];
                else {
                    toolkit.beep();
                    System.err.println("insertString: " + source[i]);
                }
            }
            super.insertString(offs, new String(result, 0, j), a);
        }
    }
}