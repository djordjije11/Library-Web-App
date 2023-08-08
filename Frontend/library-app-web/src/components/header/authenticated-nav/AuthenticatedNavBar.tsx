import { HOME_PAGE } from "../../routes/AppRouter";
import React from "react";
import {
  Navbar,
  MobileNav,
  Typography,
  Button,
} from "@material-tailwind/react";
import CloseNavMenuItemButtonIcon from "./icons/CloseNavMenuItemButtonIcon";
import OpenNavMenuItemButtonIcon from "./icons/OpenNavMenuItemButtonIcon";
import ProfileMenu from "./ProfileMenu";
import { NavList } from "./NavList";

export default function AuthenticatedNavBar() {
  const [openNav, setOpenNav] = React.useState(false);

  React.useEffect(() => {
    window.addEventListener(
      "resize",
      () => window.innerWidth >= 960 && setOpenNav(false)
    );
  }, []);

  return (
    <Navbar className="bg-gray-50 sticky top-0 z-10 h-max max-w-full rounded-none py-2 px-4 lg:px-8 lg:py-4">
      <div className="flex items-center justify-between text-blue-gray-900">
        <Typography
          as="a"
          href={HOME_PAGE}
          className="mr-4 cursor-pointer py-1.5 font-medium"
        >
          <span>Home</span>
        </Typography>
        <div className="flex items-center gap-4">
          <div className="mr-4 hidden lg:block">
            <NavList />
          </div>
          <Button
            variant="text"
            className="ml-auto h-12 w-6 text-inherit hover:bg-transparent focus:bg-transparent active:bg-transparent lg:hidden"
            ripple={false}
            onClick={() => setOpenNav(!openNav)}
          >
            {openNav ? (
              <CloseNavMenuItemButtonIcon />
            ) : (
              <OpenNavMenuItemButtonIcon />
            )}
          </Button>
          <ProfileMenu />
        </div>
      </div>
      <MobileNav open={openNav}>
        <NavList />
      </MobileNav>
    </Navbar>
  );
}
