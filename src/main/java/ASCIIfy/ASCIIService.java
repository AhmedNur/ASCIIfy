package ASCIIfy;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class ASCIIService
{

    private static final String charGradient = " .^*+#8B@";

    public BufferedImage multipartFileToBufferedImage(MultipartFile file) throws IOException {
        BufferedImage image = ImageIO.read(file.getInputStream());
        return image;
    }

    public String bufferedImageToASCII(BufferedImage image)
    {
        StringBuilder ascii = new StringBuilder();
        int imageHeight = image.getHeight();
        int imageWidth = image.getWidth();
        int pixelWidth = imageWidth > 200 ? imageWidth / 200 + 1 : 2;
        int pixelSize = pixelWidth * pixelWidth;
        double currentRed = 0;
        double currentGreen = 0;
        double currentBlue = 0;
        double currentBrightness = 0;
        Color currentPixelColor;
        for(int i = 0; i < imageHeight - pixelWidth; i+=pixelWidth)
        {
            for(int j = 0; j < imageWidth - pixelWidth; j+=pixelWidth)
            {
                for(int k = 0; k < pixelWidth; k++)
                {
                    for(int l = 0; l < pixelWidth; l++)
                    {
                        currentPixelColor = new Color(image.getRGB(j+l, i+k));
                        currentRed += currentPixelColor.getRed();
                        currentBlue += currentPixelColor.getBlue();
                        currentGreen += currentPixelColor.getGreen();
                    }
                }

                currentRed /= pixelSize;
                currentBlue /= pixelSize;
                currentGreen /= pixelSize;
                currentRed /= 255;
                currentBlue /= 255;
                currentGreen /= 255;

                currentBrightness = (0.2126 * currentRed) + (0.7152 * currentGreen) + (0.0722 * currentBlue);
                if(currentBrightness <= 0.1111)
                {
                    ascii.append(charGradient.charAt(8));
                }
                else if(currentBrightness >= 0.1111 && currentBrightness < 0.2222)
                {
                    ascii.append(charGradient.charAt(7));
                }
                else if (currentBrightness >= 0.2222 && currentBrightness < 0.3333)
                {
                    ascii.append(charGradient.charAt(6));
                }
                else if (currentBrightness >= 0.3333 && currentBrightness < 0.4444)
                {
                    ascii.append(charGradient.charAt(5));
                }
                else if (currentBrightness >= 0.4444 && currentBrightness < 0.5555)
                {
                    ascii.append(charGradient.charAt(4));
                }
                else if (currentBrightness >= 0.5555 && currentBrightness < 0.6666)
                {
                    ascii.append(charGradient.charAt(3));
                }
                else if (currentBrightness >= 0.6666 && currentBrightness < 0.7777)
                {
                    ascii.append(charGradient.charAt(2));
                }
                else if (currentBrightness >= 0.7777 && currentBrightness < 0.8888)
                {
                    ascii.append(charGradient.charAt(1));
                }
                else if (currentBrightness >= 0.8888)
                {
                    ascii.append(charGradient.charAt(0));
                }
                else
                {
                    System.out.println("Big oof." + currentBrightness);
                }
            }
            ascii.append("\n");
        }
        return ascii.toString();
    }

    public String ASCIIfy(MultipartFile file)
    {
        System.out.println(file.getContentType());
        if (file.isEmpty())
        {
            return "Error: No file uploaded.";
        }
        try
        {
            if(file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png"))
            {
                BufferedImage image = multipartFileToBufferedImage(file);
                return "<pre style=\"font-size:5px;line-height:3px;\">" + bufferedImageToASCII(image) + "</pre>";
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "Error: File uploaded was not JPEG or PNG";
    }

}
