/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.jd.quant.core.domain.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-06-15")
public class TRuntime implements org.apache.thrift.TBase<TRuntime, TRuntime._Fields>, java.io.Serializable, Cloneable, Comparable<TRuntime> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TRuntime");

  private static final org.apache.thrift.protocol.TField DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("date", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField DAYS_PASSED_FIELD_DESC = new org.apache.thrift.protocol.TField("daysPassed", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField RUN_END_FIELD_DESC = new org.apache.thrift.protocol.TField("runEnd", org.apache.thrift.protocol.TType.BOOL, (short)3);
  private static final org.apache.thrift.protocol.TField RUN_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("runTime", org.apache.thrift.protocol.TType.DOUBLE, (short)4);
  private static final org.apache.thrift.protocol.TField RUN_PERCENT_FIELD_DESC = new org.apache.thrift.protocol.TField("runPercent", org.apache.thrift.protocol.TType.I32, (short)5);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TRuntimeStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TRuntimeTupleSchemeFactory();

  public String date; // optional
  public int daysPassed; // optional
  public boolean runEnd; // optional
  public double runTime; // optional
  public int runPercent; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DATE((short)1, "date"),
    DAYS_PASSED((short)2, "daysPassed"),
    RUN_END((short)3, "runEnd"),
    RUN_TIME((short)4, "runTime"),
    RUN_PERCENT((short)5, "runPercent");

    private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // DATE
          return DATE;
        case 2: // DAYS_PASSED
          return DAYS_PASSED;
        case 3: // RUN_END
          return RUN_END;
        case 4: // RUN_TIME
          return RUN_TIME;
        case 5: // RUN_PERCENT
          return RUN_PERCENT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __DAYSPASSED_ISSET_ID = 0;
  private static final int __RUNEND_ISSET_ID = 1;
  private static final int __RUNTIME_ISSET_ID = 2;
  private static final int __RUNPERCENT_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.DATE,_Fields.DAYS_PASSED,_Fields.RUN_END,_Fields.RUN_TIME,_Fields.RUN_PERCENT};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DATE, new org.apache.thrift.meta_data.FieldMetaData("date", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DAYS_PASSED, new org.apache.thrift.meta_data.FieldMetaData("daysPassed", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.RUN_END, new org.apache.thrift.meta_data.FieldMetaData("runEnd", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.RUN_TIME, new org.apache.thrift.meta_data.FieldMetaData("runTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.RUN_PERCENT, new org.apache.thrift.meta_data.FieldMetaData("runPercent", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TRuntime.class, metaDataMap);
  }

  public TRuntime() {
    this.daysPassed = 0;

    this.runTime = 0;

  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TRuntime(TRuntime other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetDate()) {
      this.date = other.date;
    }
    this.daysPassed = other.daysPassed;
    this.runEnd = other.runEnd;
    this.runTime = other.runTime;
    this.runPercent = other.runPercent;
  }

  public TRuntime deepCopy() {
    return new TRuntime(this);
  }

  @Override
  public void clear() {
    this.date = null;
    this.daysPassed = 0;

    setRunEndIsSet(false);
    this.runEnd = false;
    this.runTime = 0;

    setRunPercentIsSet(false);
    this.runPercent = 0;
  }

  public String getDate() {
    return this.date;
  }

  public TRuntime setDate(String date) {
    this.date = date;
    return this;
  }

  public void unsetDate() {
    this.date = null;
  }

  /** Returns true if field date is set (has been assigned a value) and false otherwise */
  public boolean isSetDate() {
    return this.date != null;
  }

  public void setDateIsSet(boolean value) {
    if (!value) {
      this.date = null;
    }
  }

  public int getDaysPassed() {
    return this.daysPassed;
  }

  public TRuntime setDaysPassed(int daysPassed) {
    this.daysPassed = daysPassed;
    setDaysPassedIsSet(true);
    return this;
  }

  public void unsetDaysPassed() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __DAYSPASSED_ISSET_ID);
  }

  /** Returns true if field daysPassed is set (has been assigned a value) and false otherwise */
  public boolean isSetDaysPassed() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __DAYSPASSED_ISSET_ID);
  }

  public void setDaysPassedIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __DAYSPASSED_ISSET_ID, value);
  }

  public boolean isRunEnd() {
    return this.runEnd;
  }

  public TRuntime setRunEnd(boolean runEnd) {
    this.runEnd = runEnd;
    setRunEndIsSet(true);
    return this;
  }

  public void unsetRunEnd() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __RUNEND_ISSET_ID);
  }

  /** Returns true if field runEnd is set (has been assigned a value) and false otherwise */
  public boolean isSetRunEnd() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __RUNEND_ISSET_ID);
  }

  public void setRunEndIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __RUNEND_ISSET_ID, value);
  }

  public double getRunTime() {
    return this.runTime;
  }

  public TRuntime setRunTime(double runTime) {
    this.runTime = runTime;
    setRunTimeIsSet(true);
    return this;
  }

  public void unsetRunTime() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __RUNTIME_ISSET_ID);
  }

  /** Returns true if field runTime is set (has been assigned a value) and false otherwise */
  public boolean isSetRunTime() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __RUNTIME_ISSET_ID);
  }

  public void setRunTimeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __RUNTIME_ISSET_ID, value);
  }

  public int getRunPercent() {
    return this.runPercent;
  }

  public TRuntime setRunPercent(int runPercent) {
    this.runPercent = runPercent;
    setRunPercentIsSet(true);
    return this;
  }

  public void unsetRunPercent() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __RUNPERCENT_ISSET_ID);
  }

  /** Returns true if field runPercent is set (has been assigned a value) and false otherwise */
  public boolean isSetRunPercent() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __RUNPERCENT_ISSET_ID);
  }

  public void setRunPercentIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __RUNPERCENT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case DATE:
      if (value == null) {
        unsetDate();
      } else {
        setDate((String)value);
      }
      break;

    case DAYS_PASSED:
      if (value == null) {
        unsetDaysPassed();
      } else {
        setDaysPassed((Integer)value);
      }
      break;

    case RUN_END:
      if (value == null) {
        unsetRunEnd();
      } else {
        setRunEnd((Boolean)value);
      }
      break;

    case RUN_TIME:
      if (value == null) {
        unsetRunTime();
      } else {
        setRunTime((Double)value);
      }
      break;

    case RUN_PERCENT:
      if (value == null) {
        unsetRunPercent();
      } else {
        setRunPercent((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case DATE:
      return getDate();

    case DAYS_PASSED:
      return getDaysPassed();

    case RUN_END:
      return isRunEnd();

    case RUN_TIME:
      return getRunTime();

    case RUN_PERCENT:
      return getRunPercent();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case DATE:
      return isSetDate();
    case DAYS_PASSED:
      return isSetDaysPassed();
    case RUN_END:
      return isSetRunEnd();
    case RUN_TIME:
      return isSetRunTime();
    case RUN_PERCENT:
      return isSetRunPercent();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TRuntime)
      return this.equals((TRuntime)that);
    return false;
  }

  public boolean equals(TRuntime that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_date = true && this.isSetDate();
    boolean that_present_date = true && that.isSetDate();
    if (this_present_date || that_present_date) {
      if (!(this_present_date && that_present_date))
        return false;
      if (!this.date.equals(that.date))
        return false;
    }

    boolean this_present_daysPassed = true && this.isSetDaysPassed();
    boolean that_present_daysPassed = true && that.isSetDaysPassed();
    if (this_present_daysPassed || that_present_daysPassed) {
      if (!(this_present_daysPassed && that_present_daysPassed))
        return false;
      if (this.daysPassed != that.daysPassed)
        return false;
    }

    boolean this_present_runEnd = true && this.isSetRunEnd();
    boolean that_present_runEnd = true && that.isSetRunEnd();
    if (this_present_runEnd || that_present_runEnd) {
      if (!(this_present_runEnd && that_present_runEnd))
        return false;
      if (this.runEnd != that.runEnd)
        return false;
    }

    boolean this_present_runTime = true && this.isSetRunTime();
    boolean that_present_runTime = true && that.isSetRunTime();
    if (this_present_runTime || that_present_runTime) {
      if (!(this_present_runTime && that_present_runTime))
        return false;
      if (this.runTime != that.runTime)
        return false;
    }

    boolean this_present_runPercent = true && this.isSetRunPercent();
    boolean that_present_runPercent = true && that.isSetRunPercent();
    if (this_present_runPercent || that_present_runPercent) {
      if (!(this_present_runPercent && that_present_runPercent))
        return false;
      if (this.runPercent != that.runPercent)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetDate()) ? 131071 : 524287);
    if (isSetDate())
      hashCode = hashCode * 8191 + date.hashCode();

    hashCode = hashCode * 8191 + ((isSetDaysPassed()) ? 131071 : 524287);
    if (isSetDaysPassed())
      hashCode = hashCode * 8191 + daysPassed;

    hashCode = hashCode * 8191 + ((isSetRunEnd()) ? 131071 : 524287);
    if (isSetRunEnd())
      hashCode = hashCode * 8191 + ((runEnd) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((isSetRunTime()) ? 131071 : 524287);
    if (isSetRunTime())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(runTime);

    hashCode = hashCode * 8191 + ((isSetRunPercent()) ? 131071 : 524287);
    if (isSetRunPercent())
      hashCode = hashCode * 8191 + runPercent;

    return hashCode;
  }

  @Override
  public int compareTo(TRuntime other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetDate()).compareTo(other.isSetDate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.date, other.date);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDaysPassed()).compareTo(other.isSetDaysPassed());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDaysPassed()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.daysPassed, other.daysPassed);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRunEnd()).compareTo(other.isSetRunEnd());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRunEnd()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.runEnd, other.runEnd);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRunTime()).compareTo(other.isSetRunTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRunTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.runTime, other.runTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRunPercent()).compareTo(other.isSetRunPercent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRunPercent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.runPercent, other.runPercent);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("TRuntime(");
    boolean first = true;

    if (isSetDate()) {
      sb.append("date:");
      if (this.date == null) {
        sb.append("null");
      } else {
        sb.append(this.date);
      }
      first = false;
    }
    if (isSetDaysPassed()) {
      if (!first) sb.append(", ");
      sb.append("daysPassed:");
      sb.append(this.daysPassed);
      first = false;
    }
    if (isSetRunEnd()) {
      if (!first) sb.append(", ");
      sb.append("runEnd:");
      sb.append(this.runEnd);
      first = false;
    }
    if (isSetRunTime()) {
      if (!first) sb.append(", ");
      sb.append("runTime:");
      sb.append(this.runTime);
      first = false;
    }
    if (isSetRunPercent()) {
      if (!first) sb.append(", ");
      sb.append("runPercent:");
      sb.append(this.runPercent);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TRuntimeStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TRuntimeStandardScheme getScheme() {
      return new TRuntimeStandardScheme();
    }
  }

  private static class TRuntimeStandardScheme extends org.apache.thrift.scheme.StandardScheme<TRuntime> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TRuntime struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.date = iprot.readString();
              struct.setDateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DAYS_PASSED
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.daysPassed = iprot.readI32();
              struct.setDaysPassedIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // RUN_END
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.runEnd = iprot.readBool();
              struct.setRunEndIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // RUN_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.runTime = iprot.readDouble();
              struct.setRunTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // RUN_PERCENT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.runPercent = iprot.readI32();
              struct.setRunPercentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TRuntime struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.date != null) {
        if (struct.isSetDate()) {
          oprot.writeFieldBegin(DATE_FIELD_DESC);
          oprot.writeString(struct.date);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetDaysPassed()) {
        oprot.writeFieldBegin(DAYS_PASSED_FIELD_DESC);
        oprot.writeI32(struct.daysPassed);
        oprot.writeFieldEnd();
      }
      if (struct.isSetRunEnd()) {
        oprot.writeFieldBegin(RUN_END_FIELD_DESC);
        oprot.writeBool(struct.runEnd);
        oprot.writeFieldEnd();
      }
      if (struct.isSetRunTime()) {
        oprot.writeFieldBegin(RUN_TIME_FIELD_DESC);
        oprot.writeDouble(struct.runTime);
        oprot.writeFieldEnd();
      }
      if (struct.isSetRunPercent()) {
        oprot.writeFieldBegin(RUN_PERCENT_FIELD_DESC);
        oprot.writeI32(struct.runPercent);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TRuntimeTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TRuntimeTupleScheme getScheme() {
      return new TRuntimeTupleScheme();
    }
  }

  private static class TRuntimeTupleScheme extends org.apache.thrift.scheme.TupleScheme<TRuntime> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TRuntime struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetDate()) {
        optionals.set(0);
      }
      if (struct.isSetDaysPassed()) {
        optionals.set(1);
      }
      if (struct.isSetRunEnd()) {
        optionals.set(2);
      }
      if (struct.isSetRunTime()) {
        optionals.set(3);
      }
      if (struct.isSetRunPercent()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetDate()) {
        oprot.writeString(struct.date);
      }
      if (struct.isSetDaysPassed()) {
        oprot.writeI32(struct.daysPassed);
      }
      if (struct.isSetRunEnd()) {
        oprot.writeBool(struct.runEnd);
      }
      if (struct.isSetRunTime()) {
        oprot.writeDouble(struct.runTime);
      }
      if (struct.isSetRunPercent()) {
        oprot.writeI32(struct.runPercent);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TRuntime struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.date = iprot.readString();
        struct.setDateIsSet(true);
      }
      if (incoming.get(1)) {
        struct.daysPassed = iprot.readI32();
        struct.setDaysPassedIsSet(true);
      }
      if (incoming.get(2)) {
        struct.runEnd = iprot.readBool();
        struct.setRunEndIsSet(true);
      }
      if (incoming.get(3)) {
        struct.runTime = iprot.readDouble();
        struct.setRunTimeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.runPercent = iprot.readI32();
        struct.setRunPercentIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

