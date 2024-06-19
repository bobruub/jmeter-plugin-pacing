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

public class RedisSamplerGuiDelete extends AbstractSamplerGui {

  private static final Logger LOG = LoggerFactory.getLogger(RedisSamplerGuiPing.class);

  private final JTextField label = new JTextField();
  private final JTextField redisServer = new JTextField();
  private final JTextField redisPort = new JTextField();
  private final JTextField redisKey = new JTextField();
  //private final JTextField redisFileToLoad = new JTextField();
  //private final JTextField redisFileDelimiter = new JTextField();
  private String redisType;

  public RedisSamplerGuiDelete() {
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
    JLabel redisKeyLabel = new JLabel("Key name");
    //JLabel redisFileToLoadLabel = new JLabel("File to load");
    //JLabel redisFileDelimiterLabel = new JLabel("File delimiter");

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
                .addComponent(redisKeyLabel)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(redisKey))
                
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
                .addComponent(redisKeyLabel, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(redisKey, GroupLayout.PREFERRED_SIZE, 
                   GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE))
    );

    return redisSamplerPanel;
  }

  @Override
  public String getLabelResource() {
    return "9999 - Redis - Delete Key";
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
      redisSampler.setRedisKey(redisKey.getText());
      redisSampler.setRedisType("DELETEKEY");

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
      redisKey.setText(redisSampler.getRedisKey());
      redisType = redisSampler.getRedisType();
    }
  }
}