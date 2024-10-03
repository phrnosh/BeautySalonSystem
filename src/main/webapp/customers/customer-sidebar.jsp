<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="side-bar d-sm-none d-md-flex col-md-1 col-lg-2">

    <div class="items d-flex flex-column w-100 pt-5 justify-content-around ">

        <div class="d-flex w-100 mb-5 justify-content-center">
            <div class="d-flex w-100 mb-5 justify-content-center">
                <img class="rounded-circle bg-white" src="${sessionScope.customer.imageUrl}" alt="Customer Image"
                     height="80px" width="80px">
            </div>
        </div>


        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="customer.do">
            <div class="item-icon w-25"><i class="fa fa-user-circle"></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Profile</div>
        </a>

        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="user.do">
            <div class="item-icon w-25"><i class="fa fa-user-check"></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Account</div>
        </a>

        <a class="item d-flex justify-content-center align-items-center mb-auto ps-lg-4" href="booking.do">
            <div class="item-icon w-25"><i class="fa "></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Your Bookings</div>
        </a>


        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="logout.do">
            <div class="item-icon w-25"><i class="fa fa-sign-out"></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Exit</div>
        </a>


    </div>

</div>