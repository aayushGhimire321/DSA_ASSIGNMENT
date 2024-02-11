package Q7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.stream.Collectors;

public class SocialMediaAppGUI extends JFrame {
    private JTextArea feedTextArea;
    private JTextArea userProfileTextArea;
    private Graph socialNetworkGraph;
    private Map<String, Integer> userInteractions;
    private Map<String, Integer> contentPopularity; // Added contentPopularity

    public SocialMediaAppGUI() {
        setTitle("Social Media App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the social network graph and user interactions map
        socialNetworkGraph = new Graph();
        userInteractions = new HashMap<>();
        contentPopularity = new HashMap<>(); // Initialize contentPopularity

        // Simulated data for demonstration (moved here from generateContentRecommendation method)
        contentPopularity.put("Funny Cat Video", 100);
        contentPopularity.put("Cooking Tutorial", 80);
        contentPopularity.put("Travel Vlog", 60);
        contentPopularity.put("DIY Crafts", 50);

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

        // Create recommendation button
        JButton recommendButton = new JButton("Recommend Content");

        // Create feedback buttons
        JButton likeRecommendedButton = new JButton("Like Recommended");
        JButton dislikeRecommendedButton = new JButton("Dislike Recommended");

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

        JPanel recommendPanel = new JPanel();
        recommendPanel.add(recommendButton);

        JPanel feedbackPanel = new JPanel();
        feedbackPanel.add(likeRecommendedButton);
        feedbackPanel.add(dislikeRecommendedButton);

        mainPanel.add(feedPanel, BorderLayout.CENTER);
        mainPanel.add(profilePanel, BorderLayout.WEST);
        mainPanel.add(interactionPanel, BorderLayout.SOUTH);
        mainPanel.add(userInteractionPanel, BorderLayout.NORTH);
        mainPanel.add(recommendPanel, BorderLayout.EAST);
        mainPanel.add(feedbackPanel, BorderLayout.PAGE_END);

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

        // Action listener for recommendation button
        recommendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement action for recommend button
                String recommendedContent = generateContentRecommendation(userProfileTextArea.getText());
                feedTextArea.append("Recommended Content: " + recommendedContent + "\n");
            }
        });

        // Action listener for like recommended button
        likeRecommendedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement action for like recommended button
                String recommendedContent = feedTextArea.getText().replace("Recommended Content: ", "");
                feedTextArea.append("Liked Recommended Content: " + recommendedContent + "\n");
            }
        });

        // Action listener for dislike recommended button
        dislikeRecommendedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement action for dislike recommended button
                String recommendedContent = feedTextArea.getText().replace("Recommended Content: ", "");
                feedTextArea.append("Disliked Recommended Content: " + recommendedContent + "\n");
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

    // Generate content recommendation based on user profile
    private String generateContentRecommendation(String user) {
        // Calculate content scores based on user preferences and content popularity
        Map<String, Integer> contentScores = new HashMap<>();
        for (Map.Entry<String, Integer> entry : contentPopularity.entrySet()) {
            String content = entry.getKey();
            int popularity = entry.getValue();
            int preference = userInteractions.getOrDefault(content, 0); // Using userInteractions instead of simulated userPreferences
            int score = popularity * preference;
            contentScores.put(content, score);
        }

        // Sort content by score in descending order
        java.util.List<Map.Entry<String, Integer>> sortedContent = new ArrayList<>(contentScores.entrySet());
        sortedContent.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Filter content based on user's network influence
        Set<String> connections = socialNetworkGraph.getConnections(user);
        java.util.List<String> recommendedContent = sortedContent.stream()
                .filter(entry -> connections.contains(entry.getKey()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Return top recommendation or a random recommendation if no connection-based content is found
        if (!recommendedContent.isEmpty()) {
            return recommendedContent.get(0);
        } else {
            return sortedContent.get(0).getKey(); // Fallback to top recommendation
        }
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
            adjacencyMap.putIfAbsent(user1, new HashSet<>());
            adjacencyMap.putIfAbsent(user2, new HashSet<>());
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
