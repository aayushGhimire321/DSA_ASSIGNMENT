// Task 6
// Implement a Multithreaded Asynchronous Image Downloader in Java Swing
// Task Description:
// You are tasked with designing and implementing a multithreaded asynchronous image downloader in a Java Swing
// application. The application should allow users to enter a URL and download images from that URL in the
// background, while keeping the UI responsive. The image downloader should utilize multithreading and provide a
// smooth user experience when downloading images.
// Requirements:
// Design and implement a GUI application that allows users to enter a URL and download images.
// Implement a multithreaded asynchronous framework to handle the image downloading process in the background.
// Provide a user interface that displays the progress of each image download, including the current download status
// and completion percentage.
// [2 Marks]
// Utilize a thread pool to manage the concurrent downloading of multiple images, ensuring efficient use of system
// resources.
// [6 Marks]
// Implement a mechanism to handle downloading errors or exceptions, displaying appropriate error messages to the
// user.
// [2 Marks]
// Use thread synchronization mechanisms, such as locks or semaphores, to ensure data integrity and avoid conflicts
// during image downloading.
// [4 Marks]
// Provide options for the user to pause, resume, or cancel image downloads.
// [2 Marks]
// Test the application with various URLs containing multiple images to verify its functionality and responsiveness.
// [2 Marks]
// Include proper error handling and reporting for cases such as invalid URLs or network failures

package Q6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsyncImageDownloader extends JFrame {

    private JTextField urlTextField;
    private JButton downloadButton;
    private JButton pauseButton;
    private JButton resumeButton;
    private JButton cancelButton;
    private JTextArea logTextArea;
    private JProgressBar progressBar;

    private ExecutorService executorService;
    private Future<?> downloadTask;

    private boolean paused = false;

    public AsyncImageDownloader() {
        setTitle("Async Image Downloader");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        urlTextField = new JTextField(30);
        downloadButton = new JButton("Download");
        pauseButton = new JButton("Pause");
        resumeButton = new JButton("Resume");
        cancelButton = new JButton("Cancel");
        logTextArea = new JTextArea(10, 40);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = urlTextField.getText();
                downloadImages(url);
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paused = true;
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paused = false;
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (downloadTask != null) {
                    downloadTask.cancel(true);
                    progressBar.setValue(0);
                    logTextArea.append("Download canceled.\n");
                }
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Image URL:"));
        controlPanel.add(urlTextField);
        controlPanel.add(downloadButton);
        controlPanel.add(pauseButton);
        controlPanel.add(resumeButton);
        controlPanel.add(cancelButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(logTextArea), BorderLayout.CENTER);
        getContentPane().add(progressBar, BorderLayout.SOUTH);

        executorService = Executors.newFixedThreadPool(5);
    }

    private void downloadImages(String imageUrl) {
        downloadTask = executorService.submit(() -> {
            try {
                URL url = new URL(imageUrl);
                try (BufferedInputStream in = new BufferedInputStream(url.openStream());
                     FileOutputStream fileOutputStream = new FileOutputStream("downloaded_image.jpg")) {
                    byte[] dataBuffer = new byte[1024];
                    int bytesRead;
                    long totalBytesRead = 0;
                    long fileSize = url.openConnection().getContentLength();
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        if (paused) {
                            Thread.sleep(1000); // Simulate pause
                            continue;
                        }
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                        totalBytesRead += bytesRead;
                        int progress = (int) (totalBytesRead * 100 / fileSize);
                        SwingUtilities.invokeLater(() -> {
                            progressBar.setValue(progress);
                            logTextArea.append("Download progress: " + progress + "%\n");
                        });
                    }
                    SwingUtilities.invokeLater(() -> logTextArea.append("Download completed.\n"));
                } catch (IOException e) {
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setValue(0);
                        logTextArea.append("Error downloading image: " + e.getMessage() + "\n");
                    });
                }
            } catch (MalformedURLException e) {
                SwingUtilities.invokeLater(() -> {
                    progressBar.setValue(0);
                    logTextArea.append("Invalid URL: " + e.getMessage() + "\n");
                });
            } catch (IOException | InterruptedException e) {
                SwingUtilities.invokeLater(() -> {
                    progressBar.setValue(0);
                    logTextArea.append("Error creating URL connection: " + e.getMessage() + "\n");
                });
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AsyncImageDownloader());
    }
}
