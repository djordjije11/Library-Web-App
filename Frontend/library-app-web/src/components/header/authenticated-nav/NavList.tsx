import { Fragment } from "react";
import { ADD_MEMBER_PAGE } from "../../routes/AppRouter";
import React from "react";
import {
  Typography,
  Menu,
  MenuHandler,
  MenuList,
  MenuItem,
} from "@material-tailwind/react";
import NavListItem, { NavListItemElement } from "./NavListItem";

interface NavListMenuProps {
  menuHandlerItem: NavListItemElement;
  menuListItems: NavListItemElement[];
}

export function NavListMenu(props: NavListMenuProps) {
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

export function NavList() {
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
