from PIL import Image

img = Image.open("./img/output/1.pdf_dir/0_1.pdf.jpg")
print(img.size)
cropped = img.crop((0, 40, 1652, 1100)) # (left, upper, right, lower)
cropped.save("./img/output/1.jpg")

cropped = img.crop((0, 1100, 1652, 2300)) # (left, upper, right, lower)
cropped.save("./img/output/2.jpg")