// Name:STC.java
// Author: David Sinclair      Date: 29 Aug 2012
//
// Very basic Symbol Table implementation
//

import java.util.*;

public class STC extends Object
{
    String type;
    String value;

    public STC(String itype, String ivalue)
    {
	type = itype;
	value = ivalue;
    }
}
