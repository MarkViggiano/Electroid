package me.mark.electroid.electrical;

public interface ElectricalComponent {

  void setRotation(int rotation);
  int getRotation();
  void setVoltage(double voltage);
  double getVoltage();
  void setCurrent(double current);
  double getCurrent();
  void setResistance(double resistance);
  double getResistance();

  default double getPower() {
    return getCurrent() * getVoltage();
  }

  default double calculateVoltage() {
    double voltage = getCurrent() * getResistance();
    setVoltage(voltage);
    return voltage;
  }


}
