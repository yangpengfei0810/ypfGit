/**
 * output package name
 */
package com.kingdee.eas.cp.bc;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class VehicleEnum extends IntEnum
{
    public static final int AIRPLANE_VALUE = 10;
    public static final int TRAIN_VALUE = 20;
    public static final int SHIP_VALUE = 30;
    public static final int BUSSES_VALUE = 40;
    public static final int HIGHSPEEDTRAIN_VALUE = 6;
    public static final int OTHER_VALUE = 50;

    public static final VehicleEnum AIRPLANE = new VehicleEnum("AIRPLANE", AIRPLANE_VALUE);
    public static final VehicleEnum TRAIN = new VehicleEnum("TRAIN", TRAIN_VALUE);
    public static final VehicleEnum SHIP = new VehicleEnum("SHIP", SHIP_VALUE);
    public static final VehicleEnum BUSSES = new VehicleEnum("BUSSES", BUSSES_VALUE);
    public static final VehicleEnum HighSpeedTrain = new VehicleEnum("HighSpeedTrain", HIGHSPEEDTRAIN_VALUE);
    public static final VehicleEnum OTHER = new VehicleEnum("OTHER", OTHER_VALUE);

    /**
     * construct function
     * @param integer vehicleEnum
     */
    private VehicleEnum(String name, int vehicleEnum)
    {
        super(name, vehicleEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static VehicleEnum getEnum(String vehicleEnum)
    {
        return (VehicleEnum)getEnum(VehicleEnum.class, vehicleEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static VehicleEnum getEnum(int vehicleEnum)
    {
        return (VehicleEnum)getEnum(VehicleEnum.class, vehicleEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(VehicleEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(VehicleEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(VehicleEnum.class);
    }
}