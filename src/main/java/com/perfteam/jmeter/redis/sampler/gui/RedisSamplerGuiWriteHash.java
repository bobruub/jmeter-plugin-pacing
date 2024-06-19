package com.perfteam.jmeter.redis.sampler.gui;

import com.perfteam.jmeter.redis.sampler.RedisSampler;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisSamplerGuiWriteHash extends AbstractSamplerGui {

  private static final Logger LOG = LoggerFactory.getLogger(RedisSamplerGuiPing.class);

  private final JTextField label = new JTextField();
  private final JTextField redisServer = new JTextField();
  private final JTextField redisPort = new JTextField();
  private final JTextField redisSet = new JTextField();
  private final JTextField redisHash = new JTextField();
  private final JTextField redisHashKey = new JTextField();
  private final JTextField redisHashValue = new JTextField();
  private final JCheckBox keepData = new JCheckBox("Keep Data", true);

  private String redisType;

  public RedisSamplerGuiWriteHash() {
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
    JLabel redisHashLabel = new JLabel("Hash Key Name");
    JLabel redisHashKeyLabel = new JLabel("Hash Field Name");
    JLabel redisHashValueLabel = new JLabel("Data to write");

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
                .addComponent(redisHashLabel)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(redisHash))
            .addGroup(layout.createSequentialGroup()
                .addComponent(redisHashKeyLabel)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(redisHashKey))
            .addGroup(layout.createSequentialGroup()
                .addComponent(redisHashValueLabel)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(redisHashValue))
//            .addGroup(layout.createSequentialGroup()
//                .addComponent(redisReadMethodLabel)
//                .addPreferredGap(ComponentPlacement.RELATED)
//                .addComponent(redisReadMethod))
            //.addGroup(layout.createSequentialGroup()
            //    .addComponent(keepData))    
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
                .addComponent(redisHashLabel, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(redisHash, GroupLayout.PREFERRED_SIZE, 
                   GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE))
                .addComponent(redisHashKeyLabel, GroupLayout.PREFERRED_SIZE,
                   GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(redisHashKey, GroupLayout.PREFERRED_SIZE, 
                  GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                .addComponent(redisHashValueLabel, GroupLayout.PREFERRED_SIZE,
                  GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
               .addComponent(redisHashValue, GroupLayout.PREFERRED_SIZE, 
                 GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                //   .addGroup(layout.createParallelGroup(Alignment.LEADING)
                //.addComponent(redisReadMethodLabel, GroupLayout.PREFERRED_SIZE,
                //    GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                //.addComponent(redisReadMethod, GroupLayout.PREFERRED_SIZE, 
                //   GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE))
                //.addGroup(layout.createParallelGroup(Alignment.LEADING)
                //   .addComponent(keepData, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
                //       GroupLayout.PREFERRED_SIZE))   
                   );
    return redisSamplerPanel;
  }

  @Override
  public String getLabelResource() {
    return "9999 - Redis - HASH Write";
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
      redisSampler.setRedisHash(redisHash.getText());
      redisSampler.setRedisHashKey(redisHashKey.getText());
      redisSampler.setRedisHashValue(redisHashValue.getText());
      redisSampler.setRedisType("WRITEHASH");
      //redisSampler.setKeepData(keepData.isSelected());
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
      redisHash.setText(redisSampler.getRedisHash());
      redisHashKey.setText(redisSampler.getRedisHashKey());
      redisHashValue.setText(redisSampler.getRedisHashValue());
      redisType = redisSampler.getRedisType();
      //keepData.setSelected(redisSampler.getKeepData());
    }
  }
}
