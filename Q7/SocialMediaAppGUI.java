package Q7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class SocialMediaAppGUI extends JFrame {
    private JTextArea feedTextArea;
    private JTextArea userProfileTextArea;
    private Graph socialNetworkGraph;
    private Map<String, Integer> userInteractions;

    public SocialMediaAppGUI() {
        setTitle("Social Media App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the social network graph and user interactions map
        socialNetworkGraph = new Graph();
        userInteractions = new HashMap<>();

        // Create main feed
        feedTextArea = new JTextArea(20, 30);
        JScrollPane feedScrollPane = new JScrollPane(feedTextArea);

        // Create user profile section
        userProfileTextArea = new JTextArea(5, 20);
        JScrollPane profileScrollPane = new JScrollPane(userProfileTextArea);

        // Create interaction buttons
        JButton likeButton = new JButton("Like");
        JButton commentButton = new JButton("Comment");
        JButton shareButton = new JButton("Share");

        // Create user interaction buttons
        JButton followButton = new JButton("Follow");
        JButton createAccountButton = new JButton("Create Account");

        // Layout setup
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel feedPanel = new JPanel();
        feedPanel.add(new JLabel("Main Feed"));
        feedPanel.add(feedScrollPane);

        JPanel profilePanel = new JPanel();
        profilePanel.add(new JLabel("User Profile"));
        profilePanel.add(profileScrollPane);

        JPanel interactionPanel = new JPanel();
        interactionPanel.add(likeButton);
        interactionPanel.add(commentButton);
        interactionPanel.add(shareButton);

        JPanel userInteractionPanel = new JPanel();
        userInteractionPanel.add(followButton);
        userInteractionPanel.add(createAccountButton);

        mainPanel.add(feedPanel, BorderLayout.CENTER);
        mainPanel.add(profilePanel, BorderLayout.WEST);
        mainPanel.add(interactionPanel, BorderLayout.SOUTH);
        mainPanel.add(userInteractionPanel, BorderLayout.NORTH);

        add(mainPanel);
        setVisible(true);

        // Action listeners for interaction buttons
        likeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement action for like button
                feedTextArea.append("Liked the post!\n");
                recordInteraction("Like");
            }
        });

        commentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement action for comment button
                feedTextArea.append("Commented on the post!\n");
                recordInteraction("Comment");
            }
        });

        shareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement action for share button
                feedTextArea.append("Shared the post!\n");
                recordInteraction("Share");
            }
        });

        // Action listeners for user interaction buttons
        followButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement action for follow button
                String followedUser = JOptionPane.showInputDialog("Enter username to follow:");
                if (followedUser != null && !followedUser.isEmpty()) {
                    socialNetworkGraph.addConnection(userProfileTextArea.getText(), followedUser);
                    recordInteraction("Follow");
                }
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement action for create account button
                String username = JOptionPane.showInputDialog("Enter username to create an account:");
                if (username != null && !username.isEmpty()) {
                    socialNetworkGraph.addUser(username);
                    recordInteraction("Create Account");
                }
            }
        });
    }

    // Record user interaction
    private void recordInteraction(String interaction) {
        userInteractions.put(interaction, userInteractions.getOrDefault(interaction, 0) + 1);
        updateProfileTextArea();
    }

    // Update user profile text area based on user interactions
    private void updateProfileTextArea() {
        StringBuilder profileText = new StringBuilder();
        profileText.append("User Profile:\n");
        for (Map.Entry<String, Integer> entry : userInteractions.entrySet()) {
            profileText.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        userProfileTextArea.setText(profileText.toString());
    }

    // Graph data structure representing the social network
    class Graph {
        private Map<String, Set<String>> adjacencyMap;

        public Graph() {
            adjacencyMap = new HashMap<>();
        }

        public void addUser(String user) {
            adjacencyMap.putIfAbsent(user, new HashSet<>());
        }

        public void addConnection(String user1, String user2) {
            adjacencyMap.get(user1).add(user2);
            adjacencyMap.get(user2).add(user1); // Assuming a bidirectional connection
        }

        public Set<String> getConnections(String user) {
            return adjacencyMap.get(user);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SocialMediaAppGUI();
            }
        });
    }
}
