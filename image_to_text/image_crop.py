from PIL import Image

def image_crop(infilename, save_path):
    """
    image file 와 crop한이미지를 저장할 path 을 입력받아 crop_img를 저장한다.
    :param infilename:
        crop할 대상 image file 입력으로 넣는다.
    :param save_path:
        crop_image file의 저장 경로를 넣는다.
    :return:
    """

    img = Image.open(infilename)
    (img_w, img_h) = img.size
    print(img.size)


    # crop 할 사이즈 : grid_w, grid_h
    grid_w = img_w  # crop width
    grid_h = img_h/2.0  # crop height

    crop_img1 = img.crop((0, 0, grid_w, grid_h))
    crop_img2 = img.crop((0,grid_h,img_w,img_h))

    image_path1 = "{}_crop1.jpg".format(infilename)
    image_path2 = "{}_crop2.jpg".format(infilename)
    crop_img1.save(image_path1)
    crop_img2.save(image_path2)
    print('save file ' + '....')


if __name__ == '__main__':
    image_crop('crowling.jpg', 'pythonProject')
