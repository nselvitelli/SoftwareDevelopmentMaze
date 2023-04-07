package Remote;

import java.io.*;
import java.net.Socket;

/**
 * A mock Socket class for testing, which exposes its output for verification and allows fudging of incoming messages.
 */
public class TestSocket extends Socket {
    TestInputStream in;
    ByteArrayOutputStream out;

    public TestSocket() {
        this.in = new TestInputStream();
        this.out = new ByteArrayOutputStream(2048);
    }

    public void fakeInput(String value) {
        this.in.insertString(value);
    }

    public String readOutput() {
        PrintWriter writer = new PrintWriter(this.out);
        writer.println("hello");
        String output = this.out.toString();
        this.out.reset();
        return output;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.in;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return this.out;
    }

    private static class TestInputStream extends InputStream {
        InputStream inner = new ByteArrayInputStream(new byte[5]);

        @Override
        public int read() throws IOException {
            return this.inner.read();
        }

        void insertString(String s) {
            this.inner = new ByteArrayInputStream(s.getBytes());
        }
    }
}