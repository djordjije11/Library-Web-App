import { Fragment } from "react";
import { ADD_MEMBER_PAGE, HOME_PAGE } from "./routesUrls";
import Logout from "./Logout";
import React from "react";
import {
  Navbar,
  MobileNav,
  Typography,
  Menu,
  MenuHandler,
  MenuList,
  MenuItem,
  Button,
} from "@material-tailwind/react";

interface NavListItem {
  key?: number;
  label: string;
  link?: string;
}

interface NavListMenuProps {
  menuHandlerItem: NavListItem;
  menuListItems: NavListItem[];
}

interface NavListItemProps {
  navListItem: NavListItem;
}

function NavListMenu(props: NavListMenuProps) {
  const { menuHandlerItem, menuListItems } = props;
  const [isMenuOpen, setIsMenuOpen] = React.useState(false);

  const renderItems = menuListItems.map((navListItem) => (
    <a href={navListItem.link} key={navListItem.key}>
      <MenuItem>
        <Typography variant="h6" color="blue-gray" className="mb-1">
          {navListItem.label}
        </Typography>
      </MenuItem>
    </a>
  ));

  return (
    <Fragment>
      <Menu allowHover open={isMenuOpen} handler={setIsMenuOpen}>
        <MenuHandler>
          <Typography
            as="a"
            href={menuHandlerItem.link}
            variant="small"
            className="font-normal"
          >
            <MenuItem className="hidden items-center gap-2 text-blue-gray-900 lg:flex lg:rounded-full">
              {menuHandlerItem.label}
            </MenuItem>
          </Typography>
        </MenuHandler>
        <MenuList className="hidden w-[26rem] grid-cols-3 gap-3 overflow-visible lg:grid">
          <ul className="col-span-3 flex w-full flex-col gap-1">
            {renderItems}
          </ul>
        </MenuList>
      </Menu>
      <MenuItem className="flex items-center gap-2 text-blue-gray-900 lg:hidden">
        {menuHandlerItem.label}
      </MenuItem>
      <ul className="ml-6 flex w-full flex-col gap-1 lg:hidden">
        {renderItems}
      </ul>
    </Fragment>
  );
}

function NavListItem(props: NavListItemProps) {
  const { navListItem } = props;

  return (
    <Typography
      key={navListItem.key}
      as="a"
      href={navListItem.link}
      variant="small"
      color="blue-gray"
      className="font-normal"
    >
      <MenuItem className="flex items-center gap-2 lg:rounded-full">
        {navListItem.label}
      </MenuItem>
    </Typography>
  );
}

function NavList() {
  return (
    <ul className="mb-4 mt-2 flex flex-col gap-2 lg:mb-0 lg:mt-0 lg:flex-row lg:items-center">
      <NavListMenu
        menuHandlerItem={{ label: "Members" }}
        menuListItems={[
          {
            key: 1,
            label: "Add a new member",
            link: ADD_MEMBER_PAGE,
          },
          {
            key: 2,
            label: "List of members",
            link: "",
          },
        ]}
      />
      <NavListMenu
        menuHandlerItem={{ label: "Books" }}
        menuListItems={[
          { key: 1, label: "Add a new book", link: "" },
          { key: 2, label: "List of books", link: "" },
        ]}
      />
      <NavListItem
        navListItem={{
          key: 2,
          label: "Lendings",
          link: "",
        }}
      />
    </ul>
  );
}

function OpenNavMenuItemButtonIcon() {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      className="h-6 w-6"
      fill="none"
      stroke="currentColor"
      strokeWidth={2}
    >
      <path
        strokeLinecap="round"
        strokeLinejoin="round"
        d="M4 6h16M4 12h16M4 18h16"
      />
    </svg>
  );
}

function CloseNavMenuItemButtonIcon() {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      fill="none"
      className="h-6 w-6"
      viewBox="0 0 24 24"
      stroke="currentColor"
      strokeWidth={2}
    >
      <path
        strokeLinecap="round"
        strokeLinejoin="round"
        d="M6 18L18 6M6 6l12 12"
      />
    </svg>
  );
}

export function NavBarComponent() {
  const [openNav, setOpenNav] = React.useState(false);

  React.useEffect(() => {
    window.addEventListener(
      "resize",
      () => window.innerWidth >= 960 && setOpenNav(false)
    );
  }, []);

  return (
    <Navbar className="sticky top-0 z-10 h-max max-w-full rounded-none py-2 px-4 lg:px-8 lg:py-4">
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
          <Logout />
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
        </div>
      </div>
      <MobileNav open={openNav}>
        <NavList />
        <Logout />
      </MobileNav>
    </Navbar>
  );
}
