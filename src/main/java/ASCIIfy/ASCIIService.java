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
        String ascii = "";
        int imageHeight = image.getHeight();
        int imageWidth = image.getWidth();
        double currentRed = 0;
        double currentGreen = 0;
        double currentBlue = 0;
        double currentBrightness = 0;
        Color currentPixelColor;
        for(int i = 0; i < imageHeight - 2; i+=2)
        {
            for(int j = 0; j < imageWidth - 2; j+=2)
            {
                for(int k = 0; k < 2; k++)
                {
                    for(int l = 0; l < 2; l++)
                    {
                        currentPixelColor = new Color(image.getRGB(j+l, i+k));
                        currentRed += currentPixelColor.getRed();
                        currentBlue += currentPixelColor.getBlue();
                        currentGreen += currentPixelColor.getGreen();
                    }
                }

                currentRed /= 4;
                currentBlue /= 4;
                currentGreen /= 4;
                currentRed /= 255;
                currentBlue /= 255;
                currentGreen /= 255;

                currentBrightness = (0.2126 * currentRed) + (0.7152 * currentGreen) + (0.0722 * currentBlue);
                if(currentBrightness <= 0.1111)
                {
                    ascii += charGradient.charAt(8);
                }
                else if(currentBrightness >= 0.1111 && currentBrightness < 0.2222)
                {
                    ascii += charGradient.charAt(7);
                }
                else if (currentBrightness >= 0.2222 && currentBrightness < 0.3333)
                {
                    ascii += charGradient.charAt(6);
                }
                else if (currentBrightness >= 0.3333 && currentBrightness < 0.4444)
                {
                    ascii += charGradient.charAt(5);
                }
                else if (currentBrightness >= 0.4444 && currentBrightness < 0.5555)
                {
                    ascii += charGradient.charAt(4);
                }
                else if (currentBrightness >= 0.5555 && currentBrightness < 0.6666)
                {
                    ascii += charGradient.charAt(3);
                }
                else if (currentBrightness >= 0.6666 && currentBrightness < 0.7777)
                {
                    ascii += charGradient.charAt(2);
                }
                else if (currentBrightness >= 0.7777 && currentBrightness < 0.8888)
                {
                    ascii += charGradient.charAt(1);
                }
                else if (currentBrightness >= 0.8888)
                {
                    ascii += charGradient.charAt(0);
                }
                else
                {
                    System.out.println("Big oof." + currentBrightness);
                }
            }
            ascii += "\n";
        }
        return ascii;
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
