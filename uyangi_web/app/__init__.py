import sys
from flask import Flask, render_template, request, flash, redirect
from app import mod_dbconn
from datetime import datetime
from selenium.webdriver.support.ui import Select
from selenium.webdriver.support.select import Select


app = Flask(__name__)

@app.route('/')
def home():
    return render_template('home.html')

@app.route('/err')
def review_err():
    return render_template('review_test.html')

# 여기부터 추가
@app.route('/db')
def select():
    db_class = mod_dbconn.Database()

    sql = "SELECT ProductName, IMG_URL, ProductID \
                FROM products order by purchaseAmount"
    row = db_class.executeAll(sql)
    print(row)

    return render_template('total.html', resultData=row)

# 주문 조회
@app.route('/orders')
def select_orders():
    try:
        db_class = mod_dbconn.Database()

        sql = "SELECT * FROM orders"
        orders = db_class.executeAll(sql)

        print(orders)

        return render_template('orders.html', orders=orders)
    except Exception:
        flash('주문관리 페이지 오류')
        return redirect('/')

# 주문 삭제
@app.route('/delete-orders/<int:OrderNumber>/')
def delete_orders(OrderNumber):
    try:
        db_class = mod_dbconn.Database()
        sql = "DELETE FROM orders WHERE OrderNumber = {}".format(OrderNumber)
        db_class.execute(sql)
        db_class.commit()
        return redirect('/orders')
    except Exception:
        return redirect('/orders')


#주문 수정
# @app.route('/edit-orders/<int:OrderNumber>/<string:OrderStatus>')
@app.route('/edit_orders', methods=['POST'])
def edit_orders():
    OrderNumber = request.form['OrderNumber']
    OrderStatus = request.form['OrderStatus']
    print(OrderStatus + "" + OrderNumber)
    # OrderStatus = "상품준비중"
    db_class = mod_dbconn.Database()
    sql = "UPDATE orders SET OrderStatus = %s WHERE OrderNumber = %s"
    db_class.executeAll(sql, (OrderStatus, OrderNumber))
    db_class.commit()
    return redirect('/orders')

# 주문 날짜 조회
@app.route('/orderDateSearch', methods=['POST'])
def orderDateSearch():
    db_class = mod_dbconn.Database()

    orderDateSearchA = request.form['orderDateSearchA']
    orderDateSearchB = request.form['orderDateSearchB']
    print(orderDateSearchA + " " + orderDateSearchB)

    sql = "SELECT * FROM orders WHERE OrderDate between %s and %s"
    searchOrderDate = db_class.executeAll(sql, (orderDateSearchA, orderDateSearchB))

    print(searchOrderDate)

    return render_template('orders.html', orders=searchOrderDate)

# 주문 상태 조회
@app.route('/orderStatusSearch', methods=['POST'])
def orderStatusSearch():
    db_class = mod_dbconn.Database()

    orderStatusSearch = request.form['orderStatusSearch']
    print(orderStatusSearch)

    sql = "SELECT * FROM orders WHERE OrderStatus = %s"
    searchOrderStatus = db_class.executeAll(sql, (orderStatusSearch))

    print(searchOrderStatus)

    return render_template('orders.html', orders=searchOrderStatus)


@app.route('/product_register', methods=['GET'])
def product_register():
    return render_template('product_register.html')


@app.route('/product_register_process', methods=['POST'])
def product_register_process():

    select_ProductID = request.form['select_ProductID']                 #1
    select_ProductName = request.form['select_ProductName']             #2
    select_Description = request.form['select_Description']             #3

    select_Price = request.form['select_Price']                         #4

    file = request.files['file']                   #5

    select_IMG_URL ="https://i.imgur.com/YAmvnKQ.png"
    select_catid = request.form['select_catid']                        #6
    select_length = request.form['select_length']                       #7
    select_style = request.form['select_style']                         #8
    select_thickness = request.form['select_thickness']                 #9
    select_stock_number = request.form['select_stock_number']           #10
    select_purchaseAmount = 0
    select_item_sell_status = "SELL"

    elect_detailImg = request.files['select']                 #11


    select_detailImg = "'https://i.imgur.com/Pvq7Xon.jpg'"
    select_reg_time = datetime.today().strftime("%Y-%m-%d %H:%M")
    select_update_time = ""

    cnc = 1
    cnc = request.form['cnc']
    cn = int(cnc)
    cstr = []
    print(cn)

    for i in range(0, cn):
        print(i)
        scn = "select_color_num" + str(i)
        print(scn)
        cstr.append(request.form[scn])
        print(cstr[i])

    colorstr1 = "블랙"
    colorstr2 = "블루"

    colorh1 = "#000000"
    colorh2 = "#2c67a9"



    db_class = mod_dbconn.Database()

    # global indexnum
    # indexnum += indexnum
    # tt=500

    sql = "INSERT INTO products (ProductID, ProductName, Description, Price, IMG_URL, catid, length, style, thickness, purchaseAmount, item_sell_status, reg_time, stock_number, update_time) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"
    db_class.executeAll(sql, (select_ProductID, select_ProductName, select_Description, select_Price, select_IMG_URL, select_catid, select_length, select_style, select_thickness, select_purchaseAmount, select_item_sell_status, select_reg_time, select_stock_number, select_update_time))

    sql2 = "INSERT INTO products_image (ProductID, detailImg1) VALUES (%s, %s)"
    db_class.executeAll(sql2, (select_ProductID, select_detailImg))

    sql3 = "INSERT INTO color (ProductID, product_color, HEX) VALUES (%s, %s, %s)"
    db_class.executeAll(sql3, (select_ProductID, colorstr1, colorh1))

    sql4 = "INSERT INTO color (ProductID, product_color, HEX) VALUES (%s, %s, %s)"
    db_class.executeAll(sql3, (select_ProductID, colorstr2, colorh2))

    db_class.commit()
    return render_template("home.html")





# 여기부터 추가
@app.route('/review')
def select_review():
    db_class = mod_dbconn.Database()

    # id 받기
    productId = '5033869899'
    productId = request.args.get('productID')
    print("PRODUCTID: " + str(productId));


    sql_key = "SELECT summary1, summary2, summary3 \
                FROM keyword where pd_Id = " + productId
    key = db_class.executeAll(sql_key)

    sql_detail = "SELECT count(*) FROM d_quality WHERE pd_Id= " + productId + " AND pos=1; "
    detail1 = db_class.executeAll(sql_detail)
    sql_detail = "SELECT count(*) FROM d_quality WHERE pd_Id= " + productId + " AND pos=0; "
    detail2 = db_class.executeAll(sql_detail)
    sql_detail = "SELECT count(*) FROM d_color WHERE pd_Id= " + productId + " AND pos=1; "
    detail3 = db_class.executeAll(sql_detail)
    sql_detail = "SELECT count(*) FROM d_color WHERE pd_Id= " + productId + " AND pos=0; "
    detail4 = db_class.executeAll(sql_detail)
    sql_detail = "SELECT count(*) FROM d_price WHERE pd_Id= " + productId + " AND pos=1; "
    detail5 = db_class.executeAll(sql_detail)
    sql_detail = "SELECT count(*) FROM d_price WHERE pd_Id= " + productId + " AND pos=0; "
    detail6 = db_class.executeAll(sql_detail)
    sql_detail = "SELECT count(*) FROM d_delivery WHERE pd_Id= " + productId + " AND pos=1; "
    detail7 = db_class.executeAll(sql_detail)
    sql_detail = "SELECT count(*) FROM d_delivery WHERE pd_Id= " + productId + " AND pos=0; "
    detail8 = db_class.executeAll(sql_detail)

    sql_review_p = "SELECT count(*) FROM review WHERE product_id =" + productId + " AND pos=1"
    review_p = db_class.executeAll(sql_review_p)
    sql_review_n = "SELECT count(*) FROM review WHERE product_id =" + productId + " AND pos=0"
    review_n = db_class.executeAll(sql_review_n)

    sql_review = "SELECT user_id, product_option, content FROM review WHERE product_id =" + productId
    reviews = db_class.executeAll(sql_review)

    resultData = []
    resultData.append(key[0])
    resultData.append(detail1[0])
    resultData.append(detail2[0])
    resultData.append(detail3[0])
    resultData.append(detail4[0])
    resultData.append(detail5[0])
    resultData.append(detail6[0])
    resultData.append(detail7[0])
    resultData.append(detail8[0])
    resultData.append(review_p[0])
    resultData.append(review_n[0])
    resultData.append(reviews)

    review_p = review_p[0]['count(*)']
    review_n = review_n[0]['count(*)']
    review_p = 100 * (review_p / (review_p+review_n))
    review_n = 100 - review_p

    review_pn = []
    review_pn.append(review_p)
    review_pn.append(review_n)

    review_detail = []
    total_del = resultData[7]['count(*)'] + resultData[8]['count(*)']
    total_pri = resultData[5]['count(*)'] + resultData[6]['count(*)']
    total_qul = resultData[1]['count(*)'] + resultData[2]['count(*)']
    total_col = resultData[3]['count(*)'] + resultData[4]['count(*)']
    review_detail.append(total_del)
    review_detail.append(total_pri)
    review_detail.append(total_qul)
    review_detail.append(total_col)

    review_pos = []

    pos_del = resultData[7]['count(*)']
    neg_del = resultData[8]['count(*)']
    if pos_del == 0:
        pos_del = 1
    pos_del = 100 * (pos_del / (pos_del+neg_del))
    pos_pri = resultData[5]['count(*)']
    neg_pri = resultData[6]['count(*)']
    if pos_pri == 0:
        pos_pri = 1
    pos_pri = 100 * (pos_pri / (pos_pri + neg_pri))
    pos_qul = resultData[1]['count(*)']
    neg_qul = resultData[2]['count(*)']
    if pos_qul == 0:
        pos_qul = 1
    pos_qul = 100 * (pos_qul / (pos_qul + neg_qul))
    pos_col = resultData[3]['count(*)']
    neg_col = resultData[4]['count(*)']
    if pos_col == 0:
        pos_col = 1
    pos_col = 100 * (pos_col / (pos_col + neg_col))

    review_pos.append(pos_del)
    review_pos.append(pos_pri)
    review_pos.append(pos_qul)
    review_pos.append(pos_col)

    print(review_detail)
    print(review_pos)

    return render_template('review.html', resultData=resultData, resultPN=review_pn, resultTOTAL=review_detail, resultPOS=review_pos)

# main
if __name__=='__main__':
    app.run(debug=True)

