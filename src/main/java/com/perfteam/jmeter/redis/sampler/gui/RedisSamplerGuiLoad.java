package com.perfteam.jmeter.redis.sampler.gui;

import com.perfteam.jmeter.redis.sampler.RedisSampler;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisSamplerGuiLoad extends AbstractSamplerGui {

  private static final Logger LOG = LoggerFactory.getLogger(RedisSamplerGuiPing.class);

  private final JTextField label = new JTextField();
  private final JTextField redisServer = new JTextField();
  private final JTextField redisPort = new JTextField();
  private final JTextField redisSet = new JTextField();
  private final JTextField redisFileToLoad = new JTextField();
  private String redisType;

  public RedisSamplerGuiLoad() {
    setLayout(new BorderLayout());
    setBorder(makeBorder());
    add(makeTitlePanel(), BorderLayout.NORTH);
    add(createRedisSamplerPanel(), BorderLayout.CENTER);
  }

  private JPanel createRedisSamplerPanel(){
    JPanel redisSamplerPanel = new JPanel();
    redisSamplerPanel.setBorder(BorderFactory.createTitledBorder("Redis Config"));
    GroupLayout layout = new GroupLayout(redisSamplerPanel);
    redisSamplerPanel.setLayout(layout);

    JLabel redisServerLabel = new JLabel("Server");
    JLabel redisPortLabel = new JLabel("Port");
    JLabel redisSetLabel = new JLabel("Set name");
    JLabel redisFileToLoadLabel = new JLabel("file to load");

    layout.setHorizontalGroup(
        layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(redisServerLabel)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(redisServer))
            .addGroup(layout.createSequentialGroup()
                .addComponent(redisPortLabel)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(redisPort))

            .addGroup(layout.createSequentialGroup()
                .addComponent(redisSetLabel)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(redisSet))

            .addGroup(layout.createSequentialGroup()
                .addComponent(redisFileToLoadLabel)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(redisFileToLoad))
                
                );
    layout.setVerticalGroup(
        layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                .addComponent(redisServerLabel, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(redisServer, GroupLayout.PREFERRED_SIZE, 
                    GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE))                    
            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                .addComponent(redisPortLabel, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(redisPort, GroupLayout.PREFERRED_SIZE, 
                   GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE))
                   .addGroup(layout.createParallelGroup(Alignment.LEADING)
                .addComponent(redisSetLabel, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(redisSet, GroupLayout.PREFERRED_SIZE, 
                   GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE))
                   .addGroup(layout.createParallelGroup(Alignment.LEADING)
                .addComponent(redisFileToLoadLabel, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(redisFileToLoad, GroupLayout.PREFERRED_SIZE, 
                   GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE))
                   );
    return redisSamplerPanel;
  }

  @Override
  public String getLabelResource() {
    return "9999 - Redis - SET Load";
  }

  @Override
  public String getStaticLabel() {
    return getLabelResource();
  }

  @Override
  public TestElement createTestElement() {
    RedisSampler redisSampler = new RedisSampler();
    configureTestElement(redisSampler);
    return redisSampler;
  }

  @Override
  public void modifyTestElement(TestElement element) {
    super.configureTestElement(element);
    if (element instanceof RedisSampler) {
      RedisSampler redisSampler = (RedisSampler) element;
      redisSampler.setLabel(redisSampler.getName());
      redisSampler.setRedisServer(redisServer.getText());
      redisSampler.setRedisPort(redisPort.getText());
      redisSampler.setRedisSet(redisSet.getText());
      redisSampler.setRedisFileToLoad(redisFileToLoad.getText());
      redisSampler.setRedisType("LOAD");
    }
  }
  @Override
  public void configure(TestElement element) {
    super.configure(element);
    if (element instanceof RedisSampler) {
      RedisSampler redisSampler = (RedisSampler) element;
      label.setText(redisSampler.getName());
      redisServer.setText(redisSampler.getRedisServer());
      redisPort.setText(redisSampler.getRedisPort());
      redisSet.setText(redisSampler.getRedisSet());
      redisFileToLoad.setText(redisSampler.getRedisFileToLoad());
      redisType = redisSampler.getRedisType();
    }
  }
}