// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private final CANSparkMax m_leftDriveFront = new CANSparkMax(3, MotorType.kBrushless);
  private final CANSparkMax m_leftDriveBack = new CANSparkMax(2, MotorType.kBrushless);
  private final CANSparkMax m_rightDriveFront = new CANSparkMax(5, MotorType.kBrushless);
  private final CANSparkMax m_rightDriveBack = new CANSparkMax(6, MotorType.kBrushless); 
  private final CANSparkMax m_MotorArm = new CANSparkMax(9, MotorType.kBrushless);
  private final VictorSPX m_IntakeArm = new VictorSPX(10);


 

  private final MotorControllerGroup leftDriveGroup = new MotorControllerGroup(m_leftDriveFront, m_leftDriveBack);
  private final MotorControllerGroup rightDriveGroup = new MotorControllerGroup(m_rightDriveFront, m_rightDriveBack);


  private final DifferentialDrive m_robotDrive = new DifferentialDrive(leftDriveGroup, rightDriveGroup);
  private final Joystick m_stick = new Joystick(0);
  private final Timer m_timer = new Timer();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    leftDriveGroup.setInverted(false);
    rightDriveGroup.setInverted(false);
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      m_robotDrive.arcadeDrive(0, 0.5); // drive forwards half speed
      m_IntakeArm.set(ControlMode.PercentOutput, .5);

      
    } else {
      m_robotDrive.stopMotor(); // stop robot
      m_IntakeArm.set(ControlMode.PercentOutput, 0);

      m_timer.stop();
      
    }
  }
  // Test comment

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    m_robotDrive.arcadeDrive(m_stick.getRawAxis(4) / 2, -m_stick.getRawAxis(1));
   

    if(m_stick.getRawButton(5)){

      m_IntakeArm.set(ControlMode.PercentOutput, -.5);
    } 
    else if(m_stick.getRawButton(6)){

      m_IntakeArm.set(ControlMode.PercentOutput, .5);
    } else {
      m_IntakeArm.set(ControlMode.PercentOutput, 0);

    }


    if(m_stick.getRawAxis(2) > .2){

      m_MotorArm.set(1);
    } 
    else if(m_stick.getRawAxis(3) > .2){

      m_MotorArm.set(-.7);
    } else {
      m_MotorArm.set(0);

    }
  
    SmartDashboard.putNumber("Left Y", m_stick.getRawAxis(1));
    SmartDashboard.putNumber("Right X", m_stick.getRawAxis(2));
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
